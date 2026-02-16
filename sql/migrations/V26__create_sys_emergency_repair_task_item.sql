CREATE TABLE IF NOT EXISTS sys_emergency_repair_task (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(64) NOT NULL,
  report_user VARCHAR(100),
  report_time DATETIME,
  fix_user VARCHAR(100),
  fix_time DATETIME,
  remark VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_repair_task_task_no (task_no)
);

CREATE TABLE IF NOT EXISTS sys_emergency_repair_item (
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
  UNIQUE KEY uk_repair_item_task_box (task_id, box_id),
  KEY idx_repair_item_box_id (box_id),
  CONSTRAINT fk_repair_item_task FOREIGN KEY (task_id) REFERENCES sys_emergency_repair_task(id),
  CONSTRAINT fk_repair_item_box FOREIGN KEY (box_id) REFERENCES sys_box(id)
);
