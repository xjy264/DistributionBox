package com.xjy.shujia.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 代码生成器
 * by xjy
 * @since 2022-07-14
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }
    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/distribution?serverTimezone=GMT%2b8", "root", "xjy12345")
                .globalConfig(builder -> {
                    builder.author("xjy") // 设置作者
                            .outputDir(System.getProperty("user.dir") + "/src/main/java/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xjy.shujia") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.mapperBuilder().enableBaseResultMap().build();
                    builder.controllerBuilder().enableHyphenStyle().enableRestStyle();//驼峰转连字符
                    builder.addInclude("sys_record") // 设置需要生成的表名
                            .addTablePrefix("sys_", "s_"); // 设置过滤表前缀

                })
                .execute();
    }
}
