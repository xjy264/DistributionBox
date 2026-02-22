package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.InspectionTaskSaveDto;
import com.distributionbox.controller.dto.InspectionTaskViewDto;
import com.distributionbox.entity.InspectionItem;
import com.distributionbox.entity.InspectionTask;
import com.distributionbox.mapper.InspectionItemMapper;
import com.distributionbox.service.IInspectionTaskService;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/maintenance-task", "/inspection-task"})
public class InspectionTaskController {

    @Resource
    private IInspectionTaskService inspectionTaskService;

    @Resource
    private InspectionItemMapper inspectionItemMapper;

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result save(@RequestBody InspectionTaskSaveDto dto) {
        InspectionTask task = new InspectionTask();
        BeanUtils.copyProperties(dto, task);
        if (task.getInspectionTime() == null) {
            task.setInspectionTime(LocalDateTime.now());
        }
        if (task.getTaskNo() == null || task.getTaskNo().isBlank()) {
            return Result.error("400", "taskNo不能为空（手动填写）");
        }
        inspectionTaskService.saveOrUpdate(task);

        if (dto.getItems() != null) {
            inspectionItemMapper.deleteByTaskId(task.getId());
            List<InspectionItem> validItems = normalizeItems(dto.getItems());
            for (InspectionItem item : validItems) {
                item.setId(null);
                item.setTaskId(task.getId());
                inspectionItemMapper.insert(item);
            }
        }
        return Result.success(task.getId());
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable Integer id) {
        inspectionItemMapper.deleteByTaskId(id);
        return Result.success(inspectionTaskService.removeById(id));
    }

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String inspectionUser,
                       @RequestParam(defaultValue = "") String taskNo,
                       @RequestParam(defaultValue = "") String inspectionTime,
                       @RequestParam(defaultValue = "") String boxAccount) {
        QueryWrapper<InspectionTask> queryWrapper = new QueryWrapper<>();
        if (!inspectionUser.isBlank()) {
            queryWrapper.like("inspection_user", inspectionUser.trim());
        }
        if (!taskNo.isBlank()) {
            queryWrapper.like("task_no", taskNo.trim());
        }
        if (!inspectionTime.isBlank()) {
            queryWrapper.like("inspection_time", inspectionTime.trim());
        }
        if (!boxAccount.isBlank()) {
            List<Integer> taskIds = inspectionItemMapper.selectTaskIdsByBoxAccount(boxAccount.trim());
            if (taskIds == null || taskIds.isEmpty()) {
                return Result.success(Map.of(
                        "records", Collections.emptyList(),
                        "total", 0,
                        "current", pageNum,
                        "size", pageSize
                ));
            }
            queryWrapper.in("id", taskIds);
        }
        queryWrapper.orderByDesc("id");
        IPage<InspectionTask> page = inspectionTaskService.page(new Page<>(pageNum, pageSize), queryWrapper);

        List<InspectionTaskViewDto> records = page.getRecords().stream().map(task -> {
            InspectionTaskViewDto view = new InspectionTaskViewDto();
            BeanUtils.copyProperties(task, view);
            List<Integer> boxIds = inspectionItemMapper.selectBoxIdsByTaskId(task.getId());
            view.setBoxIds(boxIds);
            view.setBoxCount(boxIds.size());
            view.setBoxAccounts(inspectionItemMapper.selectBoxAccountsByTaskId(task.getId()));
            return view;
        }).collect(Collectors.toList());

        return Result.success(Map.of(
                "records", records,
                "total", page.getTotal(),
                "current", page.getCurrent(),
                "size", page.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        InspectionTask task = inspectionTaskService.getById(id);
        if (task == null) {
            return Result.success(null);
        }
        InspectionTaskViewDto view = new InspectionTaskViewDto();
        BeanUtils.copyProperties(task, view);

        QueryWrapper<InspectionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_id", id).orderByAsc("id");
        List<InspectionItem> items = inspectionItemMapper.selectList(queryWrapper);
        view.setItems(items);
        List<Integer> boxIds = items.stream().map(InspectionItem::getBoxId).toList();
        view.setBoxIds(boxIds);
        view.setBoxCount(boxIds.size());
        view.setBoxAccounts(inspectionItemMapper.selectBoxAccountsByTaskId(id));
        return Result.success(view);
    }

    private List<InspectionItem> normalizeItems(List<InspectionItem> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        List<InspectionItem> result = new ArrayList<>();
        for (InspectionItem item : items) {
            if (item == null || item.getBoxId() == null || item.getBoxId() <= 0) {
                continue;
            }
            result.add(item);
        }
        return result;
    }
}
