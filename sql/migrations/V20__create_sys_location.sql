CREATE TABLE IF NOT EXISTS sys_location (
  id INT PRIMARY KEY AUTO_INCREMENT,
  parent_id INT NOT NULL DEFAULT 0,
  name VARCHAR(255) NOT NULL,
  level TINYINT NOT NULL COMMENT '1=车间,2=工区,3=安装地点',
  sort_num INT NOT NULL DEFAULT 0,
  UNIQUE KEY uq_level_parent_name (level, parent_id, name),
  INDEX idx_parent_id (parent_id)
);

-- 1) 从 old-box/myvue/src/views/home.vue 提取的车间数据（level=1）
INSERT INTO sys_location (parent_id, name, level, sort_num) VALUES
(0, '秦皇岛房建车间', 1, 10),
(0, '茶坞房建车间', 1, 20),
(0, '湖东房建车间', 1, 30),
(0, '大同东房建车间', 1, 40),
(0, '大同西房建车间', 1, 50),
(0, '大同南房建车间', 1, 60),
(0, '朔州房建车间', 1, 70),
(0, '原平房建车间', 1, 80),
(0, '太原北房建车间', 1, 90)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- 2) 从 old-box/myvue/src/views/home.vue 提取的工区数据（level=2）
INSERT INTO sys_location (parent_id, name, level, sort_num)
SELECT s.id, x.area_name, 2, x.sort_num
FROM (
  SELECT '秦皇岛房建车间' AS station_name, '西张庄综合维修工区' AS area_name, 10 AS sort_num UNION ALL
  SELECT '秦皇岛房建车间', '秦东综合维修工区', 20 UNION ALL
  SELECT '秦皇岛房建车间', '柳村综合维修工区', 30 UNION ALL
  SELECT '秦皇岛房建车间', '迁安综合维修工区', 40 UNION ALL
  SELECT '秦皇岛房建车间', '遵化综合维修工区', 50 UNION ALL
  SELECT '茶坞房建车间', '茶坞综合维修工区', 10 UNION ALL
  SELECT '茶坞房建车间', '蓟县西综合维修工区', 20 UNION ALL
  SELECT '湖东房建车间', '湖东供热维修工区', 10 UNION ALL
  SELECT '湖东房建车间', '湖东房建维修工区', 20 UNION ALL
  SELECT '湖东房建车间', '逐鹿综合维修工区', 30 UNION ALL
  SELECT '大同东房建车间', '新华后街房建维修工区', 10 UNION ALL
  SELECT '大同东房建车间', '站北街供热维修工区', 20 UNION ALL
  SELECT '大同东房建车间', '同丰电气维修工区', 30 UNION ALL
  SELECT '大同东房建车间', '卧虎湾房建维修工区', 40 UNION ALL
  SELECT '大同东房建车间', '柴沟堡综合维修工区', 50 UNION ALL
  SELECT '大同西房建车间', '口泉综合维修工区', 10 UNION ALL
  SELECT '大同西房建车间', '大同西房建维修工区', 20 UNION ALL
  SELECT '大同西房建车间', '大同西房建巡检工区', 30 UNION ALL
  SELECT '大同西房建车间', '西线供热维修工区', 40 UNION ALL
  SELECT '大同西房建车间', '拥军路供热维修工区', 50 UNION ALL
  SELECT '大同西房建车间', '桥西供热维修工区', 60 UNION ALL
  SELECT '大同南房建车间', '大同南高铁综合维修工区', 10 UNION ALL
  SELECT '大同南房建车间', '阳高南高铁综合维修工区', 20 UNION ALL
  SELECT '大同南房建车间', '应县综合维修工区', 30 UNION ALL
  SELECT '大同南房建车间', '大同南综合工区', 40 UNION ALL
  SELECT '大同南房建车间', '大同房管工区', 50 UNION ALL
  SELECT '朔州房建车间', '朔州房建维修工区', 10 UNION ALL
  SELECT '朔州房建车间', '朔州供热维修工区', 20 UNION ALL
  SELECT '朔州房建车间', '偏关综合维修工区', 30 UNION ALL
  SELECT '原平房建车间', '忻州房建维修工区', 10 UNION ALL
  SELECT '原平房建车间', '原平房建维修工区', 20 UNION ALL
  SELECT '原平房建车间', '灵丘房建维修工区', 30 UNION ALL
  SELECT '原平房建车间', '原平供热维修工区', 40 UNION ALL
  SELECT '原平房建车间', '忻州西高铁综合维修工区', 50 UNION ALL
  SELECT '太原北房建车间', '路局西院维修工区', 10
) x
JOIN sys_location s ON s.level = 1 AND s.name = x.station_name
LEFT JOIN sys_location e ON e.level = 2 AND e.parent_id = s.id AND e.name = x.area_name
WHERE e.id IS NULL;

-- 3) 从现有 sys_box 提取安装地点（level=3）并挂到对应工区下
INSERT INTO sys_location (parent_id, name, level, sort_num)
SELECT a.id, b.box_address, 3, 0
FROM (
  SELECT DISTINCT station, area, box_address
  FROM sys_box
  WHERE IFNULL(TRIM(station), '') <> ''
    AND IFNULL(TRIM(area), '') <> ''
    AND IFNULL(TRIM(box_address), '') <> ''
) b
JOIN sys_location s ON s.level = 1 AND s.name = b.station
JOIN sys_location a ON a.level = 2 AND a.parent_id = s.id AND a.name = b.area
LEFT JOIN sys_location l ON l.level = 3 AND l.parent_id = a.id AND l.name = b.box_address
WHERE l.id IS NULL;
