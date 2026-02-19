CREATE TABLE IF NOT EXISTS sys_inspection_box (
  inspection_id INT NOT NULL,
  box_id INT NOT NULL,
  PRIMARY KEY (inspection_id, box_id),
  KEY idx_inspection_box_box (box_id),
  CONSTRAINT fk_inspection_box_inspection FOREIGN KEY (inspection_id) REFERENCES sys_inspection(id)
);
