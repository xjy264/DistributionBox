CREATE TABLE IF NOT EXISTS sys_emergency_repair_box (
  emergency_repair_id INT NOT NULL,
  box_id INT NOT NULL,
  PRIMARY KEY (emergency_repair_id, box_id),
  KEY idx_repair_box_box (box_id),
  CONSTRAINT fk_repair_box_repair FOREIGN KEY (emergency_repair_id) REFERENCES sys_emergency_repair(id)
);
