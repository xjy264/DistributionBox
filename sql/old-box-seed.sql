-- old-box 最小可运行数据集（基于现有表结构）

-- 角色
INSERT INTO sys_role (id, name, description, identity)
VALUES (1, '管理员', '系统管理员', 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name=VALUES(name), description=VALUES(description), identity=VALUES(identity);

-- 菜单（对齐 old-box 常见页面）
INSERT INTO sys_menu (id, name, path, icon, description, pid, page_path, hide_menu) VALUES
(1, '系统管理', NULL, 'el-icon-setting', '系统管理', NULL, NULL, '0'),
(2, '业务管理', NULL, 'el-icon-menu', '业务管理', NULL, NULL, '0'),
(11, '首页', '/home', 'el-icon-house', '首页', 2, 'Home', '0'),
(12, '配电箱', '/box', 'el-icon-box', '配电箱', 2, 'Box', '0'),
(13, '元器件', '/components', 'el-icon-cpu', '元器件', 2, 'Components', '0'),
(14, '巡检', '/inspection', 'el-icon-s-check', '巡检', 2, 'Inspection', '0'),
(15, '维保', '/maintenance', 'el-icon-tools', '维保', 2, 'Maintenance', '0'),
(16, '抢修', '/fix', 'el-icon-warning', '抢修', 2, 'Fix', '0'),
(17, '文件', '/files', 'el-icon-document', '文件', 2, 'Files', '0'),
(21, '用户管理', '/users', 'el-icon-user', '用户', 1, 'User', '0'),
(22, '角色管理', '/roles', 'el-icon-s-custom', '角色', 1, 'Role', '0'),
(23, '菜单管理', '/menus', 'el-icon-menu', '菜单', 1, 'Menu', '0'),
(24, '部门管理', '/departments', 'el-icon-office-building', '部门', 1, 'Department', '0')
ON DUPLICATE KEY UPDATE
  name=VALUES(name), path=VALUES(path), icon=VALUES(icon), description=VALUES(description), pid=VALUES(pid), page_path=VALUES(page_path), hide_menu=VALUES(hide_menu);

-- 角色菜单
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu;

-- 管理员账号（old-box 登录为明文密码校验）
INSERT INTO sys_user (id, username, password, nickname, role, create_time)
VALUES (1, 'admin', 'admin123', '管理员', 'ROLE_ADMIN', NOW())
ON DUPLICATE KEY UPDATE
  username=VALUES(username), password=VALUES(password), nickname=VALUES(nickname), role=VALUES(role);

-- 示例配电箱
INSERT INTO sys_box (id, box_address, box_id, station, area, size, system_url, first_url, second_url, third_url, fourth_url)
VALUES
(1, '一站台配电间', '1236547', '秦皇岛房建车间', '西张庄综合维修工区', 'XL-21', '', '', '', '', ''),
(2, '候车厅东侧设备间', '1236548', '秦皇岛房建车间', '秦东综合维修工区', 'PZ30', '', '', '', '', '')
ON DUPLICATE KEY UPDATE
  box_address=VALUES(box_address), box_id=VALUES(box_id), station=VALUES(station), area=VALUES(area), size=VALUES(size);

-- 示例元器件（box_id 复用台帐号字段）
INSERT INTO sys_components (components_name, components_unit, components_quantity, components_type, components_state, box_id, control_scope, in_specifications, out_specifications)
VALUES
('断路器', '个', '2', '施耐德 NSX100N', '正常', '1236547', '照明回路', 'YJV-4*16', 'BV-2.5'),
('接触器', '个', '1', '正泰 CJX2-3210', '正常', '1236547', '空调回路', 'YJV-4*10', 'BV-4'),
('继电器', '个', '3', '欧姆龙 MY2N', '正常', '1236548', '信号控制', 'KVV-10*1.5', 'KVV-6*1.5');
