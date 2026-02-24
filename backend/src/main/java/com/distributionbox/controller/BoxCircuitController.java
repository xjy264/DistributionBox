package com.distributionbox.controller;

import com.distributionbox.entity.BoxCircuit;
import com.distributionbox.service.IBoxCircuitService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/box-circuit")
public class BoxCircuitController {

    @Resource
    private IBoxCircuitService boxCircuitService;

    @GetMapping("/{boxId}")
    public List<BoxCircuit> byBoxId(@PathVariable Integer boxId) {
        return boxCircuitService.byBoxId(boxId);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody BoxCircuit boxCircuit) {
        return boxCircuitService.saveOrUpdate(boxCircuit);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return boxCircuitService.removeById(id);
    }
}
