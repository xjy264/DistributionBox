CREATE TABLE IF NOT EXISTS sys_user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(100),
  email VARCHAR(200),
  phone VARCHAR(50),
  address VARCHAR(255),
  create_time DATETIME,
  avatar_url VARCHAR(500),
  role VARCHAR(50)
);
