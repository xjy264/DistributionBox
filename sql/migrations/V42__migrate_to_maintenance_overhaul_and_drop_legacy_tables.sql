-- v1.42: migrate legacy inspection/repair data to maintenance/overhaul tables and drop legacy tables

CREATE TABLE IF NOT EXISTS sys_maintenance_task (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(64) NOT NULL,
  inspection_user VARCHAR(100),
  guardian_user VARCHAR(100),
  inspection_time DATETIME,
  remark VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_maintenance_task_task_no (task_no)
);

CREATE TABLE IF NOT EXISTS sys_maintenance_item (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_id INT NOT NULL,
  box_id INT NOT NULL,
  first_url VARCHAR(500),
  second_url VARCHAR(500),
  third_url VARCHAR(500),
  fourth_url VARCHAR(500),
  fifth_url VARCHAR(500),
  appearance_url VARCHAR(500),
  earlier_url VARCHAR(500),
  later_url VARCHAR(500),
  switch_model VARCHAR(100),
  current_rating VARCHAR(100),
  traverse_section VARCHAR(100),
  supply_voltage VARCHAR(100),
  a_starting VARCHAR(100),
  a_running VARCHAR(100),
  b_starting VARCHAR(100),
  b_running VARCHAR(100),
  c_starting VARCHAR(100),
  c_running VARCHAR(100),
  remark VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_maintenance_item_task_box (task_id, box_id),
  KEY idx_maintenance_item_box_id (box_id),
  CONSTRAINT fk_maintenance_item_task FOREIGN KEY (task_id) REFERENCES sys_maintenance_task(id),
  CONSTRAINT fk_maintenance_item_box FOREIGN KEY (box_id) REFERENCES sys_box(id)
);

CREATE TABLE IF NOT EXISTS sys_overhaul_task (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(64) NOT NULL,
  report_user VARCHAR(100),
  report_time DATETIME,
  fix_user VARCHAR(100),
  fix_time DATETIME,
  remark VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_overhaul_task_task_no (task_no)
);

CREATE TABLE IF NOT EXISTS sys_overhaul_item (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_id INT NOT NULL,
  box_id INT NOT NULL,
  components VARCHAR(255),
  fault_phenomenon VARCHAR(255),
  fault_reason VARCHAR(255),
  fix_process TEXT,
  remark VARCHAR(255),
  first_url VARCHAR(500),
  second_url VARCHAR(500),
  third_url VARCHAR(500),
  fourth_url VARCHAR(500),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_overhaul_item_task_box (task_id, box_id),
  KEY idx_overhaul_item_box_id (box_id),
  CONSTRAINT fk_overhaul_item_task FOREIGN KEY (task_id) REFERENCES sys_overhaul_task(id),
  CONSTRAINT fk_overhaul_item_box FOREIGN KEY (box_id) REFERENCES sys_box(id)
);

-- migrate maintenance task header (legacy: sys_inspection)
INSERT INTO sys_maintenance_task (id, task_no, inspection_user, guardian_user, inspection_time, remark)
SELECT i.id,
       CONCAT('MT', LPAD(i.id, 8, '0')),
       i.inspection_user,
       i.guardian_user,
       i.inspection_time,
       i.remark
FROM sys_inspection i
LEFT JOIN sys_maintenance_task t ON t.id = i.id
WHERE t.id IS NULL;

-- migrate maintenance items from relation table
INSERT IGNORE INTO sys_maintenance_item (
  task_id, box_id, first_url, second_url, third_url, fourth_url, fifth_url,
  appearance_url, earlier_url, later_url, switch_model, current_rating,
  traverse_section, supply_voltage, a_starting, a_running, b_starting, b_running,
  c_starting, c_running, remark
)
SELECT i.id,
       ib.box_id,
       i.first_url,
       i.second_url,
       i.third_url,
       i.fourth_url,
       i.fifth_url,
       i.appearance_url,
       i.earlier_url,
       i.later_url,
       i.switch_model,
       i.current_rating,
       i.traverse_section,
       i.supply_voltage,
       i.a_starting,
       i.a_running,
       i.b_starting,
       i.b_running,
       i.c_starting,
       i.c_running,
       i.remark
FROM sys_inspection i
JOIN sys_inspection_box ib ON ib.inspection_id = i.id;

-- fallback: migrate by pid when relation table missing
INSERT IGNORE INTO sys_maintenance_item (
  task_id, box_id, first_url, second_url, third_url, fourth_url, fifth_url,
  appearance_url, earlier_url, later_url, switch_model, current_rating,
  traverse_section, supply_voltage, a_starting, a_running, b_starting, b_running,
  c_starting, c_running, remark
)
SELECT i.id,
       CAST(i.pid AS UNSIGNED),
       i.first_url,
       i.second_url,
       i.third_url,
       i.fourth_url,
       i.fifth_url,
       i.appearance_url,
       i.earlier_url,
       i.later_url,
       i.switch_model,
       i.current_rating,
       i.traverse_section,
       i.supply_voltage,
       i.a_starting,
       i.a_running,
       i.b_starting,
       i.b_running,
       i.c_starting,
       i.c_running,
       i.remark
FROM sys_inspection i
LEFT JOIN sys_inspection_box ib ON ib.inspection_id = i.id
WHERE ib.inspection_id IS NULL
  AND i.pid REGEXP '^[0-9]+$';

-- migrate overhaul task header (legacy: sys_emergency_repair)
INSERT INTO sys_overhaul_task (id, task_no, report_user, report_time, fix_user, fix_time, remark)
SELECT r.id,
       CONCAT('OT', LPAD(r.id, 8, '0')),
       r.report_user,
       r.report_time,
       r.fix_user,
       r.fix_time,
       r.remark
FROM sys_emergency_repair r
LEFT JOIN sys_overhaul_task t ON t.id = r.id
WHERE t.id IS NULL;

-- migrate overhaul items from relation table
INSERT IGNORE INTO sys_overhaul_item (
  task_id, box_id, components, fault_phenomenon, fault_reason, fix_process, remark,
  first_url, second_url, third_url, fourth_url
)
SELECT r.id,
       rb.box_id,
       r.components,
       r.fault_phenomenon,
       r.fault_reason,
       r.fix_process,
       r.remark,
       r.first_url,
       r.second_url,
       r.third_url,
       r.fourth_url
FROM sys_emergency_repair r
JOIN sys_emergency_repair_box rb ON rb.emergency_repair_id = r.id;

-- cleanup legacy tables after migration
DROP TABLE IF EXISTS sys_inspection_box;
DROP TABLE IF EXISTS sys_inspection;
DROP TABLE IF EXISTS sys_emergency_repair_box;
DROP TABLE IF EXISTS sys_emergency_repair;
