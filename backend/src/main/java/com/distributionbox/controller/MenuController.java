package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.distributionbox.common.Constants;
import com.distributionbox.entity.Dict;
import com.distributionbox.entity.Menu;
import com.distributionbox.service.IDictService;
import com.distributionbox.service.IMenuService;
import com.distributionbox.common.Result;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2022-07-28
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @Resource
    private IDictService dictService;

    @PostMapping
    public Result save(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    @GetMapping
    public List<Menu> findAll(@RequestParam(defaultValue = "") String name) {
        return menuService.findMenus(name);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }

    @PostMapping("/del/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        return Result.success(menuService.removeBatchByIds(ids));
    }

    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictService.list(dictQueryWrapper));
    }

    @GetMapping("/ids")
    public Result findAllIds() {
        return Result.success(menuService.list().stream().map(Menu::getId));
    }
}
