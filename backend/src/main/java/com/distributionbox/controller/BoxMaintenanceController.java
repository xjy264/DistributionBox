package com.distributionbox.controller;

import com.distributionbox.common.Result;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/box-maintenance")
public class BoxMaintenanceController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final Map<String, String> TABLE_MAP = Map.of(
            "monthly", "sys_box_maintenance_monthly",
            "quarterly", "sys_box_maintenance_quarterly",
            "yearly", "sys_box_maintenance_yearly"
    );

    private String tableOf(String type) {
        String table = TABLE_MAP.get(type);
        if (table == null) throw new IllegalArgumentException("不支持的维保类型: " + type);
        return table;
    }

    @GetMapping("/{type}/page")
    public Result page(@PathVariable String type,
                       @RequestParam Integer boxId,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        try {
            String table = tableOf(type);
            if (boxId == null || boxId <= 0) return Result.error("500", "boxId不能为空");
            int offset = Math.max(pageNum - 1, 0) * pageSize;
            Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + table + " WHERE box_id = ?", Long.class, boxId);
            List<Map<String, Object>> records = jdbcTemplate.queryForList(
                    "SELECT * FROM " + table + " WHERE box_id = ? ORDER BY id DESC LIMIT ? OFFSET ?",
                    boxId, pageSize, offset
            );
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("records", records);
            data.put("total", total == null ? 0 : total);
            data.put("current", pageNum);
            data.put("size", pageSize);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }

    @GetMapping("/{type}/{id}")
    public Result getById(@PathVariable String type, @PathVariable Integer id) {
        try {
            String table = tableOf(type);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM " + table + " WHERE id = ?", id);
            return Result.success(rows.isEmpty() ? null : rows.get(0));
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }

    @PostMapping("/{type}/save")
    public Result save(@PathVariable String type, @RequestBody Map<String, Object> payload) {
        try {
            String table = tableOf(type);
            Integer boxId = asInt(payload.get("boxId"));
            if (boxId == null || boxId <= 0) return Result.error("500", "boxId不能为空");

            Integer id = asInt(payload.get("id"));
            Map<String, Object> dbMap = toDbMap(payload);
            dbMap.put("box_id", boxId);
            dbMap.put("updated_time", LocalDateTime.now());

            if (id == null) {
                dbMap.put("created_time", LocalDateTime.now());
                List<String> cols = new ArrayList<>(dbMap.keySet());
                String colSql = String.join(",", cols);
                String qSql = String.join(",", cols.stream().map(c -> "?").toList());
                Object[] args = cols.stream().map(dbMap::get).toArray();
                jdbcTemplate.update("INSERT INTO " + table + " (" + colSql + ") VALUES (" + qSql + ")", args);
            } else {
                List<String> cols = new ArrayList<>(dbMap.keySet());
                String setSql = String.join(",", cols.stream().map(c -> c + "=?").toList());
                List<Object> args = new ArrayList<>(cols.stream().map(dbMap::get).toList());
                args.add(id);
                jdbcTemplate.update("UPDATE " + table + " SET " + setSql + " WHERE id = ?", args.toArray());
            }
            return Result.success(true);
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }

    @DeleteMapping("/{type}/{id}")
    public Result delete(@PathVariable String type, @PathVariable Integer id) {
        try {
            String table = tableOf(type);
            jdbcTemplate.update("DELETE FROM " + table + " WHERE id = ?", id);
            return Result.success(true);
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }

    private Integer asInt(Object value) {
        if (value == null) return null;
        try { return Integer.valueOf(String.valueOf(value)); } catch (Exception ignore) { return null; }
    }

    private Map<String, Object> toDbMap(Map<String, Object> payload) {
        Map<String, Object> map = new LinkedHashMap<>();
        payload.forEach((k, v) -> {
            if ("id".equals(k) || "boxId".equals(k) || !StringUtils.hasText(k)) return;
            String dbKey = k.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
            map.put(dbKey, v);
        });
        return map;
    }
}
