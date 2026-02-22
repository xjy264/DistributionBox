ALTER TABLE sys_maintenance_task
  ADD COLUMN maintenance_type VARCHAR(20) NULL COMMENT 'MONTHLY/QUARTERLY/YEARLY' AFTER task_no,
  ADD COLUMN maintenance_location VARCHAR(255) NULL COMMENT '维保地点' AFTER maintenance_type,
  ADD COLUMN maintenance_content TEXT NULL COMMENT '维保工作内容' AFTER maintenance_location,
  ADD COLUMN maintenance_summary TEXT NULL COMMENT '维保工作小结' AFTER maintenance_content;

CREATE TABLE IF NOT EXISTS sys_maintenance_disease (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_id INT NOT NULL,
  seq_no INT NOT NULL,
  disease_location VARCHAR(255),
  disease_desc TEXT,
  quantity INT,
  disposal_method VARCHAR(255),
  remark VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_maintenance_disease_task (task_id),
  CONSTRAINT fk_maintenance_disease_task FOREIGN KEY (task_id) REFERENCES sys_maintenance_task(id)
);

CREATE TABLE IF NOT EXISTS sys_maintenance_compare_image (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_id INT NOT NULL,
  disease_location VARCHAR(255),
  before_image_url VARCHAR(500),
  disease_note VARCHAR(255),
  after_image_url VARCHAR(500),
  disposal_note VARCHAR(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_maintenance_compare_task (task_id),
  CONSTRAINT fk_maintenance_compare_task FOREIGN KEY (task_id) REFERENCES sys_maintenance_task(id)
);

CREATE TABLE IF NOT EXISTS sys_maintenance_process_image (
  id INT PRIMARY KEY AUTO_INCREMENT,
  task_id INT NOT NULL,
  image_url VARCHAR(500) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_maintenance_process_task (task_id),
  CONSTRAINT fk_maintenance_process_task FOREIGN KEY (task_id) REFERENCES sys_maintenance_task(id)
);

DROP TABLE IF EXISTS sys_maintenance_item;
