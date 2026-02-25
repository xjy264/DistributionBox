-- v1.95 替换旧 maintenance：配电箱维保（月/季/年）宽表

-- 删除旧 maintenance 链路表
DROP TABLE IF EXISTS sys_maintenance_process_image;
DROP TABLE IF EXISTS sys_maintenance_compare_image;
DROP TABLE IF EXISTS sys_maintenance_disease;
DROP TABLE IF EXISTS sys_maintenance_task;

CREATE TABLE IF NOT EXISTS sys_box_maintenance_monthly (
  id INT PRIMARY KEY AUTO_INCREMENT,
  box_id INT NOT NULL,
  supervise_user VARCHAR(100),
  maintenance_user VARCHAR(100),
  m1_result VARCHAR(255), m1_status VARCHAR(255), m1_remark VARCHAR(255),
  m2_result VARCHAR(255), m2_status VARCHAR(255), m2_remark VARCHAR(255),
  m3_result VARCHAR(255), m3_status VARCHAR(255), m3_remark VARCHAR(255),
  m4_result VARCHAR(255), m4_status VARCHAR(255), m4_remark VARCHAR(255),
  m5_result VARCHAR(255), m5_status VARCHAR(255), m5_remark VARCHAR(255),
  m6_result VARCHAR(255), m6_status VARCHAR(255), m6_remark VARCHAR(255),
  m7_result VARCHAR(255), m7_status VARCHAR(255), m7_remark VARCHAR(255),
  m8_result VARCHAR(255), m8_status VARCHAR(255), m8_remark VARCHAR(255),
  m9_result VARCHAR(255), m9_status VARCHAR(255), m9_remark VARCHAR(255),
  m10_result VARCHAR(255), m10_status VARCHAR(255), m10_remark VARCHAR(255),
  m11_result VARCHAR(255), m11_status VARCHAR(255), m11_remark VARCHAR(255),
  m12_result VARCHAR(255), m12_status VARCHAR(255), m12_remark VARCHAR(255),
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_bmm_box_id (box_id),
  INDEX idx_bmm_created_time (created_time)
);

CREATE TABLE IF NOT EXISTS sys_box_maintenance_quarterly (
  id INT PRIMARY KEY AUTO_INCREMENT,
  box_id INT NOT NULL,
  supervise_user VARCHAR(100),
  maintenance_user VARCHAR(100),
  q1_result VARCHAR(255), q1_status VARCHAR(255), q1_remark VARCHAR(255),
  q2_result VARCHAR(255), q2_status VARCHAR(255), q2_remark VARCHAR(255),
  q3_result VARCHAR(255), q3_status VARCHAR(255), q3_remark VARCHAR(255),
  q4_result VARCHAR(255), q4_status VARCHAR(255), q4_remark VARCHAR(255),
  q5_result VARCHAR(255), q5_status VARCHAR(255), q5_remark VARCHAR(255),
  q6_result VARCHAR(255), q6_status VARCHAR(255), q6_remark VARCHAR(255),
  q7_result VARCHAR(255), q7_status VARCHAR(255), q7_remark VARCHAR(255),
  q8_result VARCHAR(255), q8_status VARCHAR(255), q8_remark VARCHAR(255),
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_bmq_box_id (box_id),
  INDEX idx_bmq_created_time (created_time)
);

CREATE TABLE IF NOT EXISTS sys_box_maintenance_yearly (
  id INT PRIMARY KEY AUTO_INCREMENT,
  box_id INT NOT NULL,
  supervise_user VARCHAR(100),
  maintenance_user VARCHAR(100),
  y1_result VARCHAR(255), y1_status VARCHAR(255), y1_remark VARCHAR(255),
  y2_result VARCHAR(255), y2_status VARCHAR(255), y2_remark VARCHAR(255),
  y3_result VARCHAR(255), y3_status VARCHAR(255), y3_remark VARCHAR(255),
  y4_result VARCHAR(255), y4_status VARCHAR(255), y4_remark VARCHAR(255),
  y5_result VARCHAR(255), y5_status VARCHAR(255), y5_remark VARCHAR(255),
  y6_result VARCHAR(255), y6_status VARCHAR(255), y6_remark VARCHAR(255),
  y7_result VARCHAR(255), y7_status VARCHAR(255), y7_remark VARCHAR(255),
  y8_result VARCHAR(255), y8_status VARCHAR(255), y8_remark VARCHAR(255),
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_bmy_box_id (box_id),
  INDEX idx_bmy_created_time (created_time)
);
