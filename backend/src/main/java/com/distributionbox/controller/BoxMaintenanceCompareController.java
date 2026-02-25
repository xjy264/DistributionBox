package com.distributionbox.controller;

import com.distributionbox.common.Result;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
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
@RequestMapping("/box-maintenance-compare")
public class BoxMaintenanceCompareController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/list")
    public Result list(@RequestParam String type, @RequestParam Integer recordId) {
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                    "SELECT * FROM sys_box_maintenance_compare WHERE maintenance_type=? AND maintenance_record_id=? ORDER BY id DESC",
                    type, recordId
            );
            return Result.success(rows);
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }

    @PostMapping("/save")
    public Result save(@RequestBody Map<String, Object> body) {
        try {
            String type = String.valueOf(body.getOrDefault("type", ""));
            Integer recordId = asInt(body.get("recordId"));
            if (!StringUtils.hasText(type) || recordId == null || recordId <= 0) return Result.error("500", "参数错误");

            Integer id = asInt(body.get("id"));
            String diseaseLocation = asStr(body.get("diseaseLocation"));
            String beforeImageUrl = asStr(body.get("beforeImageUrl"));
            String diseaseDesc = asStr(body.get("diseaseDesc"));
            String afterImageUrl = asStr(body.get("afterImageUrl"));
            String disposalDesc = asStr(body.get("disposalDesc"));

            if (id == null) {
                jdbcTemplate.update("INSERT INTO sys_box_maintenance_compare (maintenance_type,maintenance_record_id,disease_location,before_image_url,disease_desc,after_image_url,disposal_desc,created_time,updated_time) VALUES (?,?,?,?,?,?,?,?,?)",
                        type, recordId, diseaseLocation, beforeImageUrl, diseaseDesc, afterImageUrl, disposalDesc, LocalDateTime.now(), LocalDateTime.now());
            } else {
                jdbcTemplate.update("UPDATE sys_box_maintenance_compare SET disease_location=?,before_image_url=?,disease_desc=?,after_image_url=?,disposal_desc=?,updated_time=? WHERE id=?",
                        diseaseLocation, beforeImageUrl, diseaseDesc, afterImageUrl, disposalDesc, LocalDateTime.now(), id);
            }
            return Result.success(true);
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            jdbcTemplate.update("DELETE FROM sys_box_maintenance_compare WHERE id=?", id);
            return Result.success(true);
        } catch (Exception e) {
            return Result.error("500", e.getMessage());
        }
    }

    private Integer asInt(Object value) {
        try { return value == null ? null : Integer.valueOf(String.valueOf(value)); } catch (Exception e) { return null; }
    }

    private String asStr(Object value) {
        if (value == null) return null;
        String s = String.valueOf(value).trim();
        return s.isEmpty() ? null : s;
    }
}
