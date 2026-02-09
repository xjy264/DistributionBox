CREATE TABLE IF NOT EXISTS sys_maintenance (
  id INT PRIMARY KEY AUTO_INCREMENT,
  pid VARCHAR(100),
  report_user VARCHAR(100),
  report_time DATETIME,
  fix_user VARCHAR(100),
  fix_time DATETIME,
  components VARCHAR(255),
  fault_phenomenon VARCHAR(255),
  fault_reason VARCHAR(255),
  fix_process TEXT,
  remark VARCHAR(255),
  first_url VARCHAR(500),
  second_url VARCHAR(500),
  third_url VARCHAR(500),
  fourth_url VARCHAR(500)
);
