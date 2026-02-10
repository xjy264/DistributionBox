CREATE TABLE IF NOT EXISTS sys_menu (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  path VARCHAR(255),
  icon VARCHAR(100),
  description VARCHAR(255),
  pid INT,
  page_path VARCHAR(255),
  hide_menu VARCHAR(20)
);
