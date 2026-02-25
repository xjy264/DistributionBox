-- v1.105: 月/季/年维保新增维保时间字段
ALTER TABLE sys_box_maintenance_monthly ADD COLUMN maintenance_time DATETIME NULL COMMENT '维保时间' AFTER maintenance_user;
ALTER TABLE sys_box_maintenance_quarterly ADD COLUMN maintenance_time DATETIME NULL COMMENT '维保时间' AFTER maintenance_user;
ALTER TABLE sys_box_maintenance_yearly ADD COLUMN maintenance_time DATETIME NULL COMMENT '维保时间' AFTER maintenance_user;
