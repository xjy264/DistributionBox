CREATE TABLE IF NOT EXISTS sys_home (
  id INT PRIMARY KEY AUTO_INCREMENT,
  station VARCHAR(100),
  address VARCHAR(255),
  remark VARCHAR(255),
  `user` VARCHAR(100),
  area VARCHAR(100),
  box_id VARCHAR(100),
  size VARCHAR(100)
);
