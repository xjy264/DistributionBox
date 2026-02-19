-- v1.22: unify image upload, expand URL columns for compatibility with longer signed links/CDN links
ALTER TABLE sys_box
  MODIFY COLUMN system_url VARCHAR(1000),
  MODIFY COLUMN first_url VARCHAR(1000),
  MODIFY COLUMN second_url VARCHAR(1000),
  MODIFY COLUMN third_url VARCHAR(1000),
  MODIFY COLUMN fourth_url VARCHAR(1000),
  MODIFY COLUMN fifth_url VARCHAR(1000);

ALTER TABLE sys_inspection
  MODIFY COLUMN first_url VARCHAR(1000),
  MODIFY COLUMN second_url VARCHAR(1000),
  MODIFY COLUMN third_url VARCHAR(1000),
  MODIFY COLUMN fourth_url VARCHAR(1000),
  MODIFY COLUMN fifth_url VARCHAR(1000),
  MODIFY COLUMN appearance_url VARCHAR(1000),
  MODIFY COLUMN earlier_url VARCHAR(1000),
  MODIFY COLUMN later_url VARCHAR(1000);

ALTER TABLE sys_maintenance
  MODIFY COLUMN first_url VARCHAR(1000),
  MODIFY COLUMN second_url VARCHAR(1000),
  MODIFY COLUMN third_url VARCHAR(1000),
  MODIFY COLUMN fourth_url VARCHAR(1000);

ALTER TABLE sys_emergency_repair
  MODIFY COLUMN first_url VARCHAR(1000),
  MODIFY COLUMN second_url VARCHAR(1000),
  MODIFY COLUMN third_url VARCHAR(1000),
  MODIFY COLUMN fourth_url VARCHAR(1000);

ALTER TABLE sys_inspection_task_item
  MODIFY COLUMN first_url VARCHAR(1000),
  MODIFY COLUMN second_url VARCHAR(1000),
  MODIFY COLUMN third_url VARCHAR(1000),
  MODIFY COLUMN fourth_url VARCHAR(1000),
  MODIFY COLUMN fifth_url VARCHAR(1000),
  MODIFY COLUMN appearance_url VARCHAR(1000),
  MODIFY COLUMN earlier_url VARCHAR(1000),
  MODIFY COLUMN later_url VARCHAR(1000);

ALTER TABLE sys_emergency_repair_task_item
  MODIFY COLUMN first_url VARCHAR(1000),
  MODIFY COLUMN second_url VARCHAR(1000),
  MODIFY COLUMN third_url VARCHAR(1000),
  MODIFY COLUMN fourth_url VARCHAR(1000);

ALTER TABLE sys_user
  MODIFY COLUMN avatar_url VARCHAR(1000);

ALTER TABLE sys_files
  MODIFY COLUMN url VARCHAR(1000);
