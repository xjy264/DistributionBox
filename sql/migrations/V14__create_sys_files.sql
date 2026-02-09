CREATE TABLE IF NOT EXISTS sys_files (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  type VARCHAR(50),
  size BIGINT,
  url VARCHAR(500),
  is_delete TINYINT(1),
  enable TINYINT(1),
  md5 VARCHAR(64),
  uuid VARCHAR(64)
);
