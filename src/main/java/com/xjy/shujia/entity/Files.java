package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Files对象")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

      @Schema(description = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @Schema(description = "文件名称")
      private String name;

      @Schema(description = "文件类型")
      private String type;

      @Schema(description = "文件大小")
      private Long size;

      @Schema(description = "下载链接")
      private String url;

      @Schema(description = "是否删除")
      private Boolean isDelete;

      @Schema(description = "是否禁用链接")
      private Boolean enable;

      @Schema(description = "md5")
      private String md5;

      private String uuid;
}
