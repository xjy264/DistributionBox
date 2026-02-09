CREATE TABLE IF NOT EXISTS sys_fix (
  id INT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(100),
  time DATETIME,
  address VARCHAR(255),
  result VARCHAR(255),
  user VARCHAR(100),
  remark VARCHAR(255),
  box_id INT
);
