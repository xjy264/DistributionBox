-- v1.89 检修记录关联配电箱（1个检修记录仅属于1个配电箱）
ALTER TABLE sys_overhaul_task
  ADD COLUMN box_id INT NULL COMMENT '关联配电箱ID' AFTER task_no,
  ADD INDEX idx_overhaul_task_box_id (box_id);