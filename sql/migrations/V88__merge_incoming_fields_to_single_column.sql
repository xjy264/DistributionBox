-- v1.88 进线来源/规格合并为单字段
UPDATE sys_box
SET incoming_source = CONCAT_WS(' / ', NULLIF(TRIM(incoming_source), ''), NULLIF(TRIM(incoming_spec), ''))
WHERE COALESCE(TRIM(incoming_spec), '') <> '';

ALTER TABLE sys_box
  DROP COLUMN incoming_spec;