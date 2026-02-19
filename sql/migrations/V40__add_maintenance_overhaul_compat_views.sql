-- v1.40: semantic compatibility layer for maintenance/overhaul naming
-- Keep old physical tables unchanged; expose new semantic views.

CREATE OR REPLACE VIEW sys_maintenance_task AS
SELECT * FROM sys_inspection_task;

CREATE OR REPLACE VIEW sys_maintenance_item AS
SELECT * FROM sys_inspection_item;

CREATE OR REPLACE VIEW sys_overhaul_task AS
SELECT * FROM sys_emergency_repair_task;

CREATE OR REPLACE VIEW sys_overhaul_item AS
SELECT * FROM sys_emergency_repair_item;
