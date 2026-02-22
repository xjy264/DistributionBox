-- v1.60: overhaul 主单化，删除 item 关联链路

ALTER TABLE sys_overhaul_task
  ADD COLUMN report_unit VARCHAR(255) NULL COMMENT '报修单位' AFTER task_no,
  ADD COLUMN accept_user VARCHAR(100) NULL COMMENT '报修接受人' AFTER report_user,
  ADD COLUMN supervision_user VARCHAR(100) NULL COMMENT '盯控人员' AFTER accept_user,
  ADD COLUMN rescue_users VARCHAR(255) NULL COMMENT '抢修人员' AFTER supervision_user,
  ADD COLUMN fault_phenomenon TEXT NULL COMMENT '故障现象' AFTER rescue_users,
  ADD COLUMN fault_reason TEXT NULL COMMENT '故障原因' AFTER fault_phenomenon,
  ADD COLUMN rescue_situation TEXT NULL COMMENT '抢修情况' AFTER fault_reason;

-- 仅保留报修时间，移除检修时间与旧备注字段
ALTER TABLE sys_overhaul_task
  DROP COLUMN fix_user,
  DROP COLUMN fix_time,
  DROP COLUMN remark;

DROP TABLE IF EXISTS sys_overhaul_item;
