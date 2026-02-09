package com.distributionbox.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjy
 * @since 2022-07-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      @Alias(value = "id")
      private Integer id;

      @Alias(value = "用户名")
      private String username;

      @Alias(value = "密码")
      private String password;

      @Alias(value = "昵称")
      private String nickname;

      @Alias(value = "邮箱")
      private String email;

      @Alias(value = "电话")
      private String phone;

      @Alias(value = "地址")
      private String address;

      @Alias(value = "创建时间")
      private Date createTime;

      @Alias(value = "头像")
      private String avatarUrl;

      @Alias(value = "角色")
      private String role;
}
