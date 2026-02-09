CREATE TABLE IF NOT EXISTS sys_components (
  id INT PRIMARY KEY AUTO_INCREMENT,
  components_name VARCHAR(255),
  components_unit VARCHAR(50),
  components_quantity VARCHAR(50),
  components_type VARCHAR(100),
  components_state VARCHAR(100),
  box_id VARCHAR(100),
  control_scope VARCHAR(255),
  in_specifications VARCHAR(255),
  out_specifications VARCHAR(255)
);
