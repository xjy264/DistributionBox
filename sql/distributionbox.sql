/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 90600 (9.6.0)
 Source Host           : localhost:3306
 Source Schema         : distributionbox

 Target Server Type    : MySQL
 Target Server Version : 90600 (9.6.0)
 File Encoding         : 65001

 Date: 22/02/2026 20:32:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_box
-- ----------------------------
DROP TABLE IF EXISTS `sys_box`;
CREATE TABLE `sys_box` (
  `id` int NOT NULL AUTO_INCREMENT,
  `box_address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `box_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `station` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `area` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `system_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `first_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `second_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `third_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fourth_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `size` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT 'soft delete:0 active,1 deleted',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_box
-- ----------------------------
BEGIN;
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (1, '安装地点1', 'BOX-AUTO-001', '秦皇岛房建车间', '秦东综合维修工区', '/files/828da19017504641824a4256ef7518b2.jpg', '', '', '/files/f9fe6e2a417649bfb77a24471d361090.webp', '', 'AUTO-SIZE', 0);
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (2, '安装地点1', 'new-box', '车间1', '工区A', NULL, NULL, NULL, NULL, NULL, 'NSX-250A', 0);
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (3, '安装地点1', '10086', '车间1', '工区A', NULL, NULL, NULL, NULL, NULL, 'Schneider-Easy9', 0);
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (4, '二楼', '7785', '秦皇岛房建车间', '西张庄综合维修工区', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (5, '联调A-1771136632', 'TA-1771136632-1', '测试车间', '测试工区', NULL, NULL, NULL, NULL, NULL, 'SZ-1', 1);
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (6, '联调B-1771136632', 'TA-1771136632-2', '测试车间', '测试工区', NULL, NULL, NULL, NULL, NULL, 'SZ-2', 0);
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (7, '联调A-1771136820', 'TA-1771136820-1', '测试车间', '测试工区', NULL, NULL, NULL, NULL, NULL, 'SZ-1', 1);
INSERT INTO `sys_box` (`id`, `box_address`, `box_id`, `station`, `area`, `system_url`, `first_url`, `second_url`, `third_url`, `fourth_url`, `size`, `deleted`) VALUES (8, '联调B-1771136820', 'TA-1771136820-2', '测试车间', '测试工区', NULL, NULL, NULL, NULL, NULL, 'SZ-2', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_components
-- ----------------------------
DROP TABLE IF EXISTS `sys_components`;
CREATE TABLE `sys_components` (
  `id` int NOT NULL AUTO_INCREMENT,
  `components_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `components_unit` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `components_quantity` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `components_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `components_state` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `box_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `control_scope` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `in_specifications` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `out_specifications` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_components
-- ----------------------------
BEGIN;
INSERT INTO `sys_components` (`id`, `components_name`, `components_unit`, `components_quantity`, `components_type`, `components_state`, `box_id`, `control_scope`, `in_specifications`, `out_specifications`) VALUES (1, '熔断器', '个', '2', 'TEST-Model', '正常', '1', '回路A', '10mm²', '6mm²');
INSERT INTO `sys_components` (`id`, `components_name`, `components_unit`, `components_quantity`, `components_type`, `components_state`, `box_id`, `control_scope`, `in_specifications`, `out_specifications`) VALUES (4, '测试新增元器件', '个', '1', 'TEST', '正常', '8', NULL, NULL, NULL);
INSERT INTO `sys_components` (`id`, `components_name`, `components_unit`, `components_quantity`, `components_type`, `components_state`, `box_id`, `control_scope`, `in_specifications`, `out_specifications`) VALUES (5, '1', '11', NULL, NULL, NULL, '1', NULL, NULL, NULL);
INSERT INTO `sys_components` (`id`, `components_name`, `components_unit`, `components_quantity`, `components_type`, `components_state`, `box_id`, `control_scope`, `in_specifications`, `out_specifications`) VALUES (6, '新增元器件', NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_detect
-- ----------------------------
DROP TABLE IF EXISTS `sys_detect`;
CREATE TABLE `sys_detect` (
  `pid` int NOT NULL,
  `number_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `number_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `electronic_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `electronic_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `appearance_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `appearance_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `robustness_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `robustness_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lock_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lock_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cable_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cable_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `system_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `system_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `components_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `components_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wiring_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wiring_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `loose_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `loose_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `temperature_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `temperature_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lightning_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lightning_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `jumper_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `jumper_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `inout_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `inout_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `zero_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `zero_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ground_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ground_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `barrier_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `barrier_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cleaning_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cleaning_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `screws_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `screws_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `first_other_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `first_other_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `working_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `working_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cracking_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cracking_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `workingtemperature_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `workingtemperature_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `insulation_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `insulation_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `discoloration_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `discoloration_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `order_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `order_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bridge_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bridge_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bridgeconnect_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bridgeconnect_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bridgelid_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bridgelid_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `third_state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `third_remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_detect
-- ----------------------------
BEGIN;
INSERT INTO `sys_detect` (`pid`, `number_state`, `number_remark`, `electronic_state`, `electronic_remark`, `appearance_state`, `appearance_remark`, `robustness_state`, `robustness_remark`, `lock_state`, `lock_remark`, `cable_state`, `cable_remark`, `system_state`, `system_remark`, `components_state`, `components_remark`, `wiring_state`, `wiring_remark`, `loose_state`, `loose_remark`, `temperature_state`, `temperature_remark`, `lightning_state`, `lightning_remark`, `jumper_state`, `jumper_remark`, `inout_state`, `inout_remark`, `zero_state`, `zero_remark`, `ground_state`, `ground_remark`, `barrier_state`, `barrier_remark`, `cleaning_state`, `cleaning_remark`, `screws_state`, `screws_remark`, `first_other_state`, `first_other_remark`, `working_state`, `working_remark`, `cracking_state`, `cracking_remark`, `workingtemperature_state`, `workingtemperature_remark`, `insulation_state`, `insulation_remark`, `discoloration_state`, `discoloration_remark`, `order_state`, `order_remark`, `bridge_state`, `bridge_remark`, `bridgeconnect_state`, `bridgeconnect_remark`, `bridgelid_state`, `bridgelid_remark`, `third_state`, `third_remark`) VALUES (10086, '正常', NULL, NULL, NULL, '正常', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '正常', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '显示名称',
  `value` varchar(255) NOT NULL COMMENT '值',
  `type` varchar(50) NOT NULL COMMENT '字典类型',
  `sort` int DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典配置表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `sort`, `create_time`) VALUES (1, '工区A', '工区A', 'area', 1, '2026-02-10 17:44:42');
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `sort`, `create_time`) VALUES (2, '工区B', '工区B', 'area', 2, '2026-02-10 17:44:42');
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `sort`, `create_time`) VALUES (3, '安装地点1', '安装地点1', 'address', 1, '2026-02-10 17:44:42');
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `sort`, `create_time`) VALUES (4, '安装地点2', '安装地点2', 'address', 2, '2026-02-10 17:44:42');
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `sort`, `create_time`) VALUES (5, '车间1', '车间1', 'station', 1, '2026-02-10 17:44:42');
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `sort`, `create_time`) VALUES (6, '车间2', '车间2', 'station', 2, '2026-02-10 17:44:42');
COMMIT;

-- ----------------------------
-- Table structure for sys_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_files`;
CREATE TABLE `sys_files` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `md5` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `uuid` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_files
-- ----------------------------
BEGIN;
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (1, 'images-1.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (2, 'images.webp', 'webp', 22250, '/files/f9fe6e2a417649bfb77a24471d361090.webp', NULL, NULL, 'cd5e3f45e62f7f92feff7d2f01ec6484', 'f9fe6e2a417649bfb77a24471d361090.webp');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (3, 'images-1.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (4, 'images.webp', 'webp', 22250, '/files/f9fe6e2a417649bfb77a24471d361090.webp', NULL, NULL, 'cd5e3f45e62f7f92feff7d2f01ec6484', 'f9fe6e2a417649bfb77a24471d361090.webp');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (5, 'images-1.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (6, 'images-1.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (7, 'images.webp', 'webp', 22250, '/files/f9fe6e2a417649bfb77a24471d361090.webp', NULL, NULL, 'cd5e3f45e62f7f92feff7d2f01ec6484', 'f9fe6e2a417649bfb77a24471d361090.webp');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (8, '828da19017504641824a4256ef7518b2.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (9, '828da19017504641824a4256ef7518b2.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (10, '828da19017504641824a4256ef7518b2.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (11, 'images-1.jpg', 'jpg', 49084, '/files/828da19017504641824a4256ef7518b2.jpg', NULL, NULL, 'a2eb43676a096b382b7a58cfab735142', '828da19017504641824a4256ef7518b2.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (12, '459bd4fb5c9e1f352acd12c9c2b1ba31.jpg', 'jpg', 39745, '/files/29c1877728dc4e1f87b49c77b39d39ee.jpg', NULL, NULL, '503a3c64bd645a9dd053353fdb9374df', '29c1877728dc4e1f87b49c77b39d39ee.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (13, '628aeb1f924fcc5c64ba48851340af76.jpg', 'jpg', 48964, '/files/109336b28c144019baec1d420dfa05d7.jpg', NULL, NULL, '94c1bb20add9f403c4e06a711084c0b7', '109336b28c144019baec1d420dfa05d7.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (14, '7b876e7ae7dbdd1005b4a3dbb9b4d3ec.jpg', 'jpg', 44740, '/files/13ce4b720e3d420a833519db0f74164e.jpg', NULL, NULL, 'ab9a195454685c968000a8e9cc04593a', '13ce4b720e3d420a833519db0f74164e.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (15, '68adb4386ae2481d7657af1fe8b4131b.jpg', 'jpg', 57714, '/files/2cf19bee09934b3386a6a735e7ff9f5d.jpg', NULL, NULL, '35a4a5681d3061e5905243832a0540a7', '2cf19bee09934b3386a6a735e7ff9f5d.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (16, '68adb4386ae2481d7657af1fe8b4131b.jpg', 'jpg', 57714, '/files/2cf19bee09934b3386a6a735e7ff9f5d.jpg', NULL, NULL, '35a4a5681d3061e5905243832a0540a7', '2cf19bee09934b3386a6a735e7ff9f5d.jpg');
INSERT INTO `sys_files` (`id`, `name`, `type`, `size`, `url`, `is_delete`, `enable`, `md5`, `uuid`) VALUES (17, 'f5b7a82fa10ed42453f5d3896707eac7.jpg', 'jpg', 50771, '/files/c29904dc5f4347449287d686b39b6b8c.jpg', NULL, NULL, '74494e177c30e24a30d5252b51d7e264', 'c29904dc5f4347449287d686b39b6b8c.jpg');
COMMIT;

-- ----------------------------
-- Table structure for sys_home
-- ----------------------------
DROP TABLE IF EXISTS `sys_home`;
CREATE TABLE `sys_home` (
  `id` int NOT NULL AUTO_INCREMENT,
  `station` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `area` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `box_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `size` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_home
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_location
-- ----------------------------
DROP TABLE IF EXISTS `sys_location`;
CREATE TABLE `sys_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NOT NULL DEFAULT '0',
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `level` tinyint NOT NULL COMMENT '1=è½¦é—´,2=å·¥åŒº,3=å®‰è£…åœ°ç‚¹',
  `sort_num` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_level_parent_name` (`level`,`parent_id`,`name`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_location
-- ----------------------------
BEGIN;
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (1, 0, '秦皇岛房建车间', 1, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (2, 0, '茶坞房建车间', 1, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (3, 0, '湖东房建车间', 1, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (4, 0, '大同东房建车间', 1, 40);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (5, 0, '大同西房建车间', 1, 50);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (6, 0, '大同南房建车间', 1, 60);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (7, 0, '朔州房建车间', 1, 70);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (8, 0, '原平房建车间', 1, 80);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (9, 0, '太原北房建车间', 1, 90);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (10, 1, '西张庄综合维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (11, 1, '秦东综合维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (12, 1, '柳村综合维修工区', 2, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (13, 1, '迁安综合维修工区', 2, 40);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (14, 1, '遵化综合维修工区', 2, 50);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (15, 2, '茶坞综合维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (16, 2, '蓟县西综合维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (17, 3, '湖东供热维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (18, 3, '湖东房建维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (19, 3, '逐鹿综合维修工区', 2, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (20, 4, '新华后街房建维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (21, 4, '站北街供热维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (22, 4, '同丰电气维修工区', 2, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (23, 4, '卧虎湾房建维修工区', 2, 40);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (24, 4, '柴沟堡综合维修工区', 2, 50);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (25, 5, '口泉综合维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (26, 5, '大同西房建维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (27, 5, '大同西房建巡检工区', 2, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (28, 5, '西线供热维修工区', 2, 40);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (29, 5, '拥军路供热维修工区', 2, 50);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (30, 5, '桥西供热维修工区', 2, 60);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (31, 6, '大同南高铁综合维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (32, 6, '阳高南高铁综合维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (33, 6, '应县综合维修工区', 2, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (34, 6, '大同南综合工区', 2, 40);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (35, 6, '大同房管工区', 2, 50);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (36, 7, '朔州房建维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (37, 7, '朔州供热维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (38, 7, '偏关综合维修工区', 2, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (39, 8, '忻州房建维修工区', 2, 10);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (40, 8, '原平房建维修工区', 2, 20);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (41, 8, '灵丘房建维修工区', 2, 30);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (42, 8, '原平供热维修工区', 2, 40);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (43, 8, '忻州西高铁综合维修工区', 2, 50);
INSERT INTO `sys_location` (`id`, `parent_id`, `name`, `level`, `sort_num`) VALUES (44, 9, '路局西院维修工区', 2, 10);
COMMIT;

-- ----------------------------
-- Table structure for sys_maintenance_compare_image
-- ----------------------------
DROP TABLE IF EXISTS `sys_maintenance_compare_image`;
CREATE TABLE `sys_maintenance_compare_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL,
  `disease_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `before_image_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `disease_note` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `after_image_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `disposal_note` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_maintenance_compare_task` (`task_id`),
  CONSTRAINT `fk_maintenance_compare_task` FOREIGN KEY (`task_id`) REFERENCES `sys_maintenance_task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_maintenance_compare_image
-- ----------------------------
BEGIN;
INSERT INTO `sys_maintenance_compare_image` (`id`, `task_id`, `disease_location`, `before_image_url`, `disease_note`, `after_image_url`, `disposal_note`, `created_at`, `updated_at`) VALUES (1, 3, '', '/files/29c1877728dc4e1f87b49c77b39d39ee.jpg', '4台配电柜门 警示标签老旧', '/files/109336b28c144019baec1d420dfa05d7.jpg', '对4台配电柜更换粘贴警示标签', '2026-02-22 16:24:32', '2026-02-22 19:02:13');
COMMIT;

-- ----------------------------
-- Table structure for sys_maintenance_disease
-- ----------------------------
DROP TABLE IF EXISTS `sys_maintenance_disease`;
CREATE TABLE `sys_maintenance_disease` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL,
  `seq_no` int NOT NULL,
  `disease_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `disease_desc` text COLLATE utf8mb4_general_ci,
  `quantity` int DEFAULT NULL,
  `disposal_method` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_maintenance_disease_task` (`task_id`),
  CONSTRAINT `fk_maintenance_disease_task` FOREIGN KEY (`task_id`) REFERENCES `sys_maintenance_task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_maintenance_disease
-- ----------------------------
BEGIN;
INSERT INTO `sys_maintenance_disease` (`id`, `task_id`, `seq_no`, `disease_location`, `disease_desc`, `quantity`, `disposal_method`, `remark`, `created_at`, `updated_at`) VALUES (1, 3, 1, '配电柜内', '配电柜内积灰严重', 4, '清理配电柜内灰尘', '已处理', '2026-02-22 16:19:40', '2026-02-22 18:58:48');
INSERT INTO `sys_maintenance_disease` (`id`, `task_id`, `seq_no`, `disease_location`, `disease_desc`, `quantity`, `disposal_method`, `remark`, `created_at`, `updated_at`) VALUES (2, 3, 2, '配电柜门', '配电柜门警示标签老旧', 4, '更换配电柜警示标签', '已处理', '2026-02-22 18:59:40', '2026-02-22 18:59:40');
COMMIT;

-- ----------------------------
-- Table structure for sys_maintenance_process_image
-- ----------------------------
DROP TABLE IF EXISTS `sys_maintenance_process_image`;
CREATE TABLE `sys_maintenance_process_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL,
  `image_url` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_maintenance_process_task` (`task_id`),
  CONSTRAINT `fk_maintenance_process_task` FOREIGN KEY (`task_id`) REFERENCES `sys_maintenance_task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_maintenance_process_image
-- ----------------------------
BEGIN;
INSERT INTO `sys_maintenance_process_image` (`id`, `task_id`, `image_url`, `created_at`, `updated_at`) VALUES (3, 3, '/files/13ce4b720e3d420a833519db0f74164e.jpg', '2026-02-22 19:03:41', '2026-02-22 19:03:41');
INSERT INTO `sys_maintenance_process_image` (`id`, `task_id`, `image_url`, `created_at`, `updated_at`) VALUES (4, 3, '/files/2cf19bee09934b3386a6a735e7ff9f5d.jpg', '2026-02-22 19:03:51', '2026-02-22 19:03:51');
INSERT INTO `sys_maintenance_process_image` (`id`, `task_id`, `image_url`, `created_at`, `updated_at`) VALUES (5, 3, '/files/c29904dc5f4347449287d686b39b6b8c.jpg', '2026-02-22 19:03:58', '2026-02-22 19:03:58');
COMMIT;

-- ----------------------------
-- Table structure for sys_maintenance_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_maintenance_task`;
CREATE TABLE `sys_maintenance_task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_no` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `maintenance_type` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'MONTHLY/QUARTERLY/YEARLY',
  `maintenance_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '维保地点',
  `maintenance_content` text COLLATE utf8mb4_general_ci COMMENT '维保工作内容',
  `maintenance_summary` text COLLATE utf8mb4_general_ci COMMENT '维保工作小结',
  `inspection_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `guardian_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `inspection_time` datetime DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_maintenance_task_task_no` (`task_no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_maintenance_task
-- ----------------------------
BEGIN;
INSERT INTO `sys_maintenance_task` (`id`, `task_no`, `maintenance_type`, `maintenance_location`, `maintenance_content`, `maintenance_summary`, `inspection_user`, `guardian_user`, `inspection_time`, `remark`, `created_at`, `updated_at`) VALUES (1, 'MT00000001', NULL, NULL, NULL, NULL, 'AUTO巡检员', NULL, NULL, 'AUTO_TEST', '2026-02-20 10:16:49', '2026-02-20 10:16:49');
INSERT INTO `sys_maintenance_task` (`id`, `task_no`, `maintenance_type`, `maintenance_location`, `maintenance_content`, `maintenance_summary`, `inspection_user`, `guardian_user`, `inspection_time`, `remark`, `created_at`, `updated_at`) VALUES (2, 'MT00000002', NULL, NULL, NULL, NULL, 'u1', NULL, NULL, NULL, '2026-02-20 10:16:49', '2026-02-20 10:16:49');
INSERT INTO `sys_maintenance_task` (`id`, `task_no`, `maintenance_type`, `maintenance_location`, `maintenance_content`, `maintenance_summary`, `inspection_user`, `guardian_user`, `inspection_time`, `remark`, `created_at`, `updated_at`) VALUES (3, 'MT00000003', 'MONTHLY', '行车公寓三楼西', '根据大同房建公寓段重点场所电器设备维护保养合同约定，对大同行车公寓三楼完成维保工作，具体维保工作内容如下：\n1.对4台配电柜外观检查：核查标识是否齐全，确认设备编号标识与供电系统图对应一致；检查柜门门锁是否完好且处于锁闭状态，确保密封性能良好。\n2.对4台配电柜内元器件进行检查：查看柜内（含通风孔）有无尘垢，设备工作时是否存在异常噪音与气味；检查各种指示灯是否能准确显示工作状态，元器件外观是否完好、排列是否整齐、附件是否齐全且无残缺破损。\n3.对4台配电柜内元器件的安装规范情况进行检查：确认电气安全距离符合标准要求，不同极性带电裸露部分的电气间隙处于安全范围；检查电器元件安装是否牢靠、附件是否齐全、减震措施是否完备，确保其不承受额外应力。\n4.对4台配电柜内配线实施检查：查看接线端子有无锈蚀、变色、发热及烧损现象，确认无遗留可能带电的导线及裸露端子；检查配电设备门是否有接地跨接等。\n5.对4台配电柜进行清灰、螺丝紧固、成像仪温度测量，并对相关病害进行处置。\n6.对电缆与线路及桥架线管有无破损，变色，老化检查，运行温度绝缘电阻检测等。\n7.对低压柜联络切换供电，智能断路器调试，电流互感器绝缘子检查维护保养调试，数据检测等。\n ', '      根据合同约定，大同行车公寓三楼维保工作已顺利完成，现将具体维保工作情况小结如下：\n本次维保工作涵盖4台配电柜相关设施等重点设备。针对 4 台配电柜，依次开展了外观检查，核查标识齐全性及与供电系统图的对应性，确保箱门门锁完好、锁闭且密封良好；对柜内元器件进行外观检查，查看柜内（含通风孔）有无尘垢，设备工作时是否存在异常噪音与气味，以及指示灯显示准确性、元器件外观与排列状况等；检查元器件安装规范，确认电气安全距离、带电裸露部分间隙符合标准；实施配线检查，查看接线端子状态，确保无遗留带电导线及裸露端子，同时检查配电设备门接地跨接情况。此外，还对这些配电柜进行了清灰、螺丝紧固、成像仪温度测量，对电缆与线路及桥架线管有无破损，变色，老化检查，运行温度绝缘电阻检测，对低压柜联络切换供电，智能断路器调试，电流互感器绝缘子检查维护保养调试，数据检测并处置了相关病害。\n本次维保实现了对大同行车公寓三楼4台配电柜房建设备的正常运行保障，达到了预期的工作目标。', '巡检员-1771136820', '监护-1771136820', '2026-02-15 10:00:00', '联调巡检', '2026-02-20 10:16:49', '2026-02-22 18:57:30');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `icon` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pid` int DEFAULT NULL,
  `page_path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `hide_menu` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (1, '首页', '/home', 'House', '首页', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (2, '配电箱管理', '/box', 'Box', '配电箱管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (3, '元器件管理', '/components', 'Cpu', '元器件管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (4, '巡检管理', '/inspection', 'View', '巡检管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (5, '检测管理', '/detect', 'Detection', '检测管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (6, '抽检管理', '/check', 'Checked', '抽检管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (7, '记录管理', '/record', 'Document', '记录管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (8, '维保记录', '/maintenance', 'Tools', '维护管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (9, '检修记录', '/overhaul', 'SetUp', '修理管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (10, '文件管理', '/files', 'Folder', '文件管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (11, '课程管理', '/course', 'Reading', '课程管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (12, '税务管理', '/tax', 'Money', '税务管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (13, '用户管理', '/users', 'User', '用户管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (14, '角色管理', '/roles', 'Avatar', '角色管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (15, '菜单管理', '/menus', 'Menu', '菜单管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (16, '部门管理', '/departments', 'OfficeBuilding', '部门管理', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (17, '统计图表', '/echarts', 'DataLine', '统计图表', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (18, '地图', '/map', 'Location', '地图', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`, `hide_menu`) VALUES (19, '站点管理', '/station', 'Place', '站点管理', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_overhaul_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_overhaul_item`;
CREATE TABLE `sys_overhaul_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL,
  `box_id` int NOT NULL,
  `components` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fault_phenomenon` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fault_reason` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fix_process` text COLLATE utf8mb4_general_ci,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `first_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `second_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `third_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fourth_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_overhaul_item_task_box` (`task_id`,`box_id`),
  KEY `idx_overhaul_item_box_id` (`box_id`),
  CONSTRAINT `fk_overhaul_item_box` FOREIGN KEY (`box_id`) REFERENCES `sys_box` (`id`),
  CONSTRAINT `fk_overhaul_item_task` FOREIGN KEY (`task_id`) REFERENCES `sys_overhaul_task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_overhaul_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_overhaul_item` (`id`, `task_id`, `box_id`, `components`, `fault_phenomenon`, `fault_reason`, `fix_process`, `remark`, `first_url`, `second_url`, `third_url`, `fourth_url`, `created_at`, `updated_at`) VALUES (1, 1, 6, '联调更换', '联调故障', '联调原因', '联调处理', '联调抢修', NULL, NULL, NULL, NULL, '2026-02-20 10:16:49', '2026-02-20 10:16:49');
INSERT INTO `sys_overhaul_item` (`id`, `task_id`, `box_id`, `components`, `fault_phenomenon`, `fault_reason`, `fix_process`, `remark`, `first_url`, `second_url`, `third_url`, `fourth_url`, `created_at`, `updated_at`) VALUES (2, 1, 5, '联调更换', '联调故障', '联调原因', '联调处理', '联调抢修', NULL, NULL, NULL, NULL, '2026-02-20 10:16:49', '2026-02-20 10:16:49');
INSERT INTO `sys_overhaul_item` (`id`, `task_id`, `box_id`, `components`, `fault_phenomenon`, `fault_reason`, `fix_process`, `remark`, `first_url`, `second_url`, `third_url`, `fourth_url`, `created_at`, `updated_at`) VALUES (3, 2, 8, '联调更换', '联调故障', '联调原因', '联调处理', '联调抢修', NULL, NULL, NULL, NULL, '2026-02-20 10:16:49', '2026-02-20 10:16:49');
INSERT INTO `sys_overhaul_item` (`id`, `task_id`, `box_id`, `components`, `fault_phenomenon`, `fault_reason`, `fix_process`, `remark`, `first_url`, `second_url`, `third_url`, `fourth_url`, `created_at`, `updated_at`) VALUES (4, 2, 7, '联调更换', '联调故障', '联调原因', '联调处理', '联调抢修', NULL, NULL, NULL, NULL, '2026-02-20 10:16:49', '2026-02-20 10:16:49');
COMMIT;

-- ----------------------------
-- Table structure for sys_overhaul_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_overhaul_task`;
CREATE TABLE `sys_overhaul_task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_no` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `report_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `report_time` datetime DEFAULT NULL,
  `fix_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fix_time` datetime DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_overhaul_task_task_no` (`task_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_overhaul_task
-- ----------------------------
BEGIN;
INSERT INTO `sys_overhaul_task` (`id`, `task_no`, `report_user`, `report_time`, `fix_user`, `fix_time`, `remark`, `created_at`, `updated_at`) VALUES (1, 'OT00000001', '报修-1771136632', '2026-02-14 16:00:00', '抢修-1771136632', '2026-02-14 16:00:00', '联调抢修', '2026-02-20 10:16:49', '2026-02-20 10:16:49');
INSERT INTO `sys_overhaul_task` (`id`, `task_no`, `report_user`, `report_time`, `fix_user`, `fix_time`, `remark`, `created_at`, `updated_at`) VALUES (2, 'OT00000002', '报修-1771136820', '2026-02-14 16:00:00', '抢修-1771136820', '2026-02-14 16:00:00', '联调抢修', '2026-02-20 10:16:49', '2026-02-20 10:16:49');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `identity` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `name`, `description`, `identity`) VALUES (1, '超级管理员', '拥有所有权限', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 1);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 2);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 3);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 4);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 5);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 6);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 7);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 8);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 9);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 10);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 11);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 12);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 13);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 14);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 15);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 16);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 17);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 18);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 19);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `avatar_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (1, 'admin', '123456', '超级管理员', 'admin@example.com', NULL, NULL, '2026-02-10 11:43:58', NULL, 'ROLE_ADMIN');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (4, 'qa_api', 'qa_api_123', '联调账号', NULL, NULL, NULL, '2026-02-15 14:22:17', NULL, 'ROLE_ADMIN');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (5, 'testuser210204', '123456', '普通用户', NULL, NULL, NULL, NULL, NULL, 'ROLE_USER');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (6, 'u124_1771374914', '123456', 'v124用户', NULL, NULL, NULL, NULL, NULL, 'TECHNICIAN');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (7, 'u124_1771375058', '123456', 'v124用户', NULL, NULL, NULL, NULL, NULL, 'TECHNICIAN');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (8, 'u124_1771375101', '123456', 'v124用户', NULL, NULL, NULL, NULL, NULL, 'TECHNICIAN');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
