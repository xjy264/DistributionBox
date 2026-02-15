package com.xjy.shujia.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "User对象", description = "用户实体")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @Schema(description = "id")
      @TableId(value = "id", type = IdType.AUTO)
      @Alias(value = "id")
      private Integer id;

      @Schema(description = "用户名")
      @Alias(value = "用户名")
      private String username;

      @Schema(description = "密码")
      @Alias(value = "密码")
      private String password;

      @Schema(description = "昵称")
      @Alias(value = "昵称")
      private String nickname;

      @Schema(description = "邮箱")
      @Alias(value = "邮箱")
      private String email;

      @Schema(description = "电话")
      @Alias(value = "电话")
      private String phone;

      @Schema(description = "地址")
      @Alias(value = "地址")
      private String address;

      @Schema(description = "创建时间")
      @Alias(value = "创建时间")
      private Date createTime;

      @Schema(description = "头像")
      @Alias(value = "头像")
      private String avatarUrl;

      @Schema(description = "角色")
      @Alias(value = "角色")
      private String role;
}
