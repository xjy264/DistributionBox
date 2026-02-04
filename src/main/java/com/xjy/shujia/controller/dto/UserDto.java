package com.xjy.shujia.controller.dto;

import cn.hutool.core.annotation.Alias;
import com.xjy.shujia.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *接受前端登录请求
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Alias("id")
    private Integer id;
    @Alias("username")
    private String username;
    @Alias("password")
    private String password;
    @Alias("nickname")
    private String nickname;
    @Alias("avatarUrl")
    private String avatarUrl;
    @Alias("token")
    private String token;
    @Alias("menus")
    private List<Menu> menus;
    @Alias("role")
    private String role;
}
