-- v1.110: 维保详情新增病害前后对比与工程图片模块

CREATE TABLE IF NOT EXISTS sys_box_maintenance_compare (
  id INT PRIMARY KEY AUTO_INCREMENT,
  maintenance_type VARCHAR(20) NOT NULL COMMENT 'monthly/quarterly/yearly',
  maintenance_record_id INT NOT NULL COMMENT '维保主记录ID',
  disease_location VARCHAR(255) NULL COMMENT '病害位置',
  before_image_url VARCHAR(1000) NULL COMMENT '维保前图片',
  disease_desc TEXT NULL COMMENT '病害说明',
  after_image_url VARCHAR(1000) NULL COMMENT '维保后图片',
  disposal_desc TEXT NULL COMMENT '处置说明',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_mbmc_record (maintenance_type, maintenance_record_id)
);

CREATE TABLE IF NOT EXISTS sys_box_maintenance_process_image (
  id INT PRIMARY KEY AUTO_INCREMENT,
  maintenance_type VARCHAR(20) NOT NULL COMMENT 'monthly/quarterly/yearly',
  maintenance_record_id INT NOT NULL COMMENT '维保主记录ID',
  image_url VARCHAR(1000) NULL COMMENT '工程图片',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_mbmpi_record (maintenance_type, maintenance_record_id)
);
