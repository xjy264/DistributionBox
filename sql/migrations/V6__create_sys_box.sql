CREATE TABLE IF NOT EXISTS sys_box (
  id INT PRIMARY KEY AUTO_INCREMENT,
  box_address VARCHAR(255),
  box_id VARCHAR(100),
  station VARCHAR(100),
  area VARCHAR(100),
  system_url VARCHAR(500),
  first_url VARCHAR(500),
  second_url VARCHAR(500),
  third_url VARCHAR(500),
  fourth_url VARCHAR(500),
  size VARCHAR(100)
);
