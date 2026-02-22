-- v1.43 remove redundant legacy business tables after code cleanup
DROP TABLE IF EXISTS student_course;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS sys_check;
DROP TABLE IF EXISTS sys_fix;
DROP TABLE IF EXISTS sys_maintenance;
DROP TABLE IF EXISTS sys_record;
DROP TABLE IF EXISTS sys_tax;
