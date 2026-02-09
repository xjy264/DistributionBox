# DistributionBox 完成说明

## 概览
本次从零实现了 DistributionBox 系统（后端 Spring Boot 3 + 前端 Vue 3），保留 old-box 的模块与字段结构，并完成 RBAC、文件上传、巡检/维保/抢修等业务模块。

## 已实现模块
- Auth：登录/注册、JWT、RBAC
- 用户(User)、角色(Role)、菜单(Menu)、角色菜单(RoleMenu)、部门(Department)
- 配电箱(Box)、组件(Components)
- 巡检(Inspection)、检测(Detect)、检查(Check)、记录(Record)
- 维保(Maintenance)、抢修(Fix)
- 文件/附件(Files)
- 课程(Course) + 选课(StudentCourse)
- 税务(Tax)、首页(Home)
- 图表(Echarts)
- 地图/站点/人员页面（前端展示）

## 迁移脚本
- V1__create_sys_user.sql
- V2__create_sys_role.sql
- V3__create_sys_menu.sql
- V4__create_sys_role_menu.sql
- V5__create_sys_department.sql
- V6__create_sys_box.sql
- V7__create_sys_components.sql
- V8__create_sys_inspection.sql
- V9__create_sys_detect.sql
- V10__create_sys_check.sql
- V11__create_sys_record.sql
- V12__create_sys_maintenance.sql
- V13__create_sys_fix.sql
- V14__create_sys_files.sql
- V15__create_course.sql
- V16__create_student_course.sql
- V17__create_sys_tax.sql
- V18__create_sys_home.sql
- V19__create_sys_dict.sql

## 运行方式
1. 启动 MySQL 8
   - `docker compose up -d`
2. 启动后端
   - `cd backend`
   - `mvn clean package`
   - `mvn spring-boot:run`
3. 启动前端
   - `cd frontend`
   - `npm install`
   - `npm run dev`

## 账号初始化
- 管理员账号需手动插入数据库。
- 密码使用 BCrypt 存储（需提前加密后写入 `sys_user.password`）。

## 备注
- 文件上传默认路径为项目根目录 `./files`。
- Swagger UI: `http://localhost:8080/swagger-ui.html`
