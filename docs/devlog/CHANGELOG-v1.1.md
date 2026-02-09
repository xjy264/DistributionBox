# DistributionBox v1.1 开发变更记录

> 规则：每完成一个模块/功能点，都在这里记录：做了什么、对应需求文档章节、对应 SQL 迁移文件。

## 2026-02-04
- 初始化：创建 docs/demands/v1.1.md，确定重构目标与技术栈（Java21 + Vue3 + MySQL8）。
- 规划：补充 v1.1 实施计划与模块/基础工程检查清单。
- 基础工程：创建 `backend/` Spring Boot 3 项目、`frontend/` Vue3 项目、`docker-compose.yml`、`files/` 目录与 README 启动说明。
- 认证与权限：登录/注册、JWT、Spring Security、RBAC 角色菜单绑定与菜单树输出。
- 业务模块：Box、Components、Inspection、Detect、Check、Record、Maintenance、Fix、Files、Course+StudentCourse、Tax、Home、Echarts。
- 系统模块：User、Role、Menu、Department、Dict（菜单图标）。
- 迁移脚本：新增 V1-V19 分模块建表脚本（见 docs/demands/v1.1.md 11.2）。
