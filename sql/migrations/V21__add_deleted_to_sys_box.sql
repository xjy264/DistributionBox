ALTER TABLE sys_box
ADD COLUMN deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'soft delete: 0 active, 1 deleted';
