-- v1.87 配电箱新增字段 + 回路字段精简
ALTER TABLE sys_box
  ADD COLUMN box_no VARCHAR(100) NULL COMMENT '配电箱编号' AFTER box_id,
  ADD COLUMN incoming_source VARCHAR(255) NULL COMMENT '进线来源' AFTER indoor_outdoor,
  ADD COLUMN incoming_spec VARCHAR(255) NULL COMMENT '进线规格' AFTER incoming_source;

ALTER TABLE sys_box_circuit
  DROP COLUMN switch_model,
  DROP COLUMN rated_current,
  DROP COLUMN wire_section,
  DROP COLUMN power_voltage;