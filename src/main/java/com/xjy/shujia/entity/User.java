package com.xjy.shujia.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.utility.nullability.AlwaysNull;

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
@ApiModel(value = "User对象", description = "")//swagger注解
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
      @Alias(value = "id")
      private Integer id;

      @ApiModelProperty("用户名")
      @Alias(value = "用户名")
      private String username;

      @ApiModelProperty("密码")
      @Alias(value = "密码")
      private String password;

      @ApiModelProperty("昵称")
      @Alias(value = "昵称")
      private String nickname;

      @ApiModelProperty("邮箱")
      @Alias(value = "邮箱")
      private String email;

      @ApiModelProperty("电话")
      @Alias(value = "电话")
      private String phone;

      @ApiModelProperty("地址")
      @Alias(value = "地址")
      private String address;

      @ApiModelProperty("创建时间")
      @Alias(value = "创建时间")
      private Date createTime;

      @ApiModelProperty("头像")
      @Alias(value = "头像")
      private String avatarUrl;

      @ApiModelProperty("角色")
      @Alias(value = "角色")
      private String role;
}
