-- v1.78 配电箱新增共用/大功率联动字段
ALTER TABLE sys_box
  ADD COLUMN shared_with_others VARCHAR(10) NULL COMMENT '是否与其它单位共用(是/否)' AFTER indoor_outdoor,
  ADD COLUMN shared_scope VARCHAR(255) NULL COMMENT '共用范围' AFTER shared_with_others,
  ADD COLUMN high_power_appliance VARCHAR(10) NULL COMMENT '是否为大功率电器(是/否)' AFTER shared_scope,
  ADD COLUMN high_power_name VARCHAR(255) NULL COMMENT '大功率电器名称' AFTER high_power_appliance;