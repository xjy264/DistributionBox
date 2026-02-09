package com.distributionbox.controller;

import cn.hutool.core.util.StrUtil;
import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.UserDto;
import com.distributionbox.entity.User;
import com.distributionbox.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        String username=userDto.getUsername();
        String password=userDto.getPassword();
        if (StrUtil.isBlankIfStr(username)||StrUtil.isBlankIfStr(password)){
            return Result.error("400","参数错误");
        }
        UserDto dto = userService.login(userDto);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        String username=user.getUsername();
        String password=user.getPassword();
        if (StrUtil.isBlankIfStr(username)||StrUtil.isBlankIfStr(password)){
            return Result.error("400","参数错误");
        }
        user = userService.register(user);
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        userService.save(user);
        return Result.success(user);
    }
}
