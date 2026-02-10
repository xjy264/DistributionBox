package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjy
 * @since 2022-07-22
 */
@Getter
@Setter
  @TableName("sys_files")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String name;

      private String type;

      private Long size;

      private String url;

      private Boolean isDelete;

      private Boolean enable;

      private String md5;

      private String uuid;
}
