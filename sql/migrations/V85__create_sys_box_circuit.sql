-- v1.85 新增配电箱回路表
CREATE TABLE IF NOT EXISTS sys_box_circuit (
  id INT PRIMARY KEY AUTO_INCREMENT,
  box_id INT NOT NULL,
  supply_circuit VARCHAR(100),
  switch_model VARCHAR(100),
  rated_current VARCHAR(100),
  wire_section VARCHAR(100),
  power_voltage VARCHAR(100),
  start_current VARCHAR(100),
  run_current VARCHAR(100),
  power VARCHAR(100),
  electric_device VARCHAR(255),
  device_location VARCHAR(500),
  remark VARCHAR(500),
  INDEX idx_box_circuit_box_id (box_id)
);