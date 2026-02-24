-- v1.69 配电箱字段扩展：车站/明桩暗桩/室内室外

ALTER TABLE sys_box
  ADD COLUMN railway_station VARCHAR(100) NULL COMMENT '车站' AFTER box_id,
  ADD COLUMN pile_type VARCHAR(20) NULL COMMENT '明桩/暗桩' AFTER size,
  ADD COLUMN indoor_outdoor VARCHAR(20) NULL COMMENT '室内/室外' AFTER pile_type;
