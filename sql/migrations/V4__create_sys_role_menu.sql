CREATE TABLE IF NOT EXISTS sys_role_menu (
  role_id INT NOT NULL,
  menu_id INT NOT NULL,
  PRIMARY KEY (role_id, menu_id)
);
