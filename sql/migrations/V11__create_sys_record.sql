CREATE TABLE IF NOT EXISTS sys_record (
  id INT PRIMARY KEY AUTO_INCREMENT,
  terminal_name VARCHAR(255),
  temperature VARCHAR(100),
  running_current VARCHAR(100),
  starting_current VARCHAR(100),
  leakage VARCHAR(100),
  voltage VARCHAR(100),
  insulation VARCHAR(100),
  ground VARCHAR(100),
  remark VARCHAR(255),
  number VARCHAR(100),
  pid VARCHAR(100)
);
