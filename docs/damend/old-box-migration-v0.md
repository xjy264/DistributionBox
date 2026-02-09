# old-box 功能迁移需求（草案 v0）

> 目标：将 `old-box/`（历史 Spring Boot 2.7 + Vue2 项目）中的核心业务功能迁移到当前 `DistributionBox/` 项目结构下（`backend/` + `frontend/`）。
>
> 本文档用于梳理“需要迁移什么 + 迁移到哪里 + 验收标准是什么”。

## 1. 背景与范围

### 1.1 源项目（old-box）概览
- 后端：Spring Boot **2.7.1**，Java 8，MyBatis/MyBatis-Plus，JWT 拦截器，Swagger（springfox 3）
- 数据：MySQL（示例库名 `shujia`），Redis（用于部分能力/缓存，具体以代码为准）
- 文件上传：本地文件系统目录（`files.upload.path`），并通过静态资源映射对外暴露
- 前端：`old-box/myvue/`（Vue2）

old-box 后端主要模块（从代码结构推断）：
- 登录/注册：`POST /login`、`POST /register`
- 用户与权限：User、Role、Menu、RoleMenu
- 业务模块：
  - Box（配电箱资产）
  - Department（部门）
  - Maintenance（维护/保养）
  - Inspection（巡检）
  - Detect（检测）
  - Fix（维修/故障处理）
  - Files（文件上传/管理）
  - Record（记录/台账）
  - Components（组件/部件）
  - Tax（？可能是台账/统计类，需确认业务含义）
  - Dict（字典）
  - Course（课程/培训？）
  - Check、Home、Echarts（看板/统计）

### 1.2 目标项目（DistributionBox）约束
- 目录结构：
  - `backend/`：Spring Boot 3（Java 21）API
  - `frontend/`：Vue 3 + Vite + TS UI
  - `sql/migrations/`：Flyway migrations
  - `files/`：上传文件本地存储根目录
  - `docs/`：需求与开发文档

> 迁移实现不要求与 old-box 保持代码一致，但**功能与数据能力需覆盖**。

## 2. 迁移目标（要交付的能力）

### 2.1 认证与账号体系
1. 支持登录、注册
   - 对齐 old-box：`/login`、`/register`
   - 目标项目允许调整为 `/api/auth/login` 等，但需提供兼容层或前端同步改造
2. JWT 鉴权
   - 类似 old-box 的 `JwtInterceptor`
   - 后端需在未登录访问受限资源时返回统一错误码/错误结构

**验收标准**
- 登录成功返回 token（或等效会话），访问受保护接口时携带 token 可正常访问
- token 缺失/失效时返回明确的 401/403 与可读错误信息

### 2.2 权限与菜单（RBAC）
1. 用户管理：增删改查、分页、禁用/启用（如 old-box 支持）
2. 角色管理：角色 CRUD
3. 菜单管理：菜单树/路由配置
4. 角色-菜单关联：RoleMenu

**验收标准**
- 不同角色登录后可看到的菜单/页面与接口权限一致

### 2.3 配电箱资产（Box）
- 配电箱资产的 CRUD
- 字段至少覆盖：位置、型号、容量/等级、照片、备注（以 old-box 实际字段为准）

**验收标准**
- 前端可新增/编辑/查看/删除配电箱
- 后端接口具备分页与条件查询（如按位置/关键字）

### 2.4 巡检 / 检测 / 维修 / 维护模块
将 old-box 对应模块能力迁移：
- Inspection（巡检）
- Detect（检测）
- Fix（维修/故障）
- Maintenance（维护/保养）
- Check（如为检查单/检查项）

**验收标准**
- 每个模块均具备：列表（分页）+ 新增 + 编辑 + 详情 + 删除（如业务允许）
- 关键业务字段与 old-box 保持一致或提供映射说明

### 2.5 文件管理（Files）
1. 上传文件到 `DistributionBox/files/`（或配置目录）
2. 数据表记录文件元信息（文件名、URL、大小、上传人、时间、关联业务对象等）
3. 提供下载/预览 URL

**验收标准**
- 上传后可在页面看到文件记录并可下载
- 文件存储路径与 URL 映射明确，生产环境可配置

### 2.6 字典/基础数据（Dict / Department / Components 等）
- Department：部门组织
- Dict：字典项（下拉选项、状态等）
- Components：部件/元件库（如果在业务中被引用）

**验收标准**
- 被业务表单依赖的基础数据在新系统中可维护并可正常引用

### 2.7 统计看板（Home / Echarts / Record / Tax）
- 迁移 old-box 的看板/统计接口与页面（若仍需要）
- 明确每个统计指标的计算口径与数据来源

**验收标准**
- 主要统计图表可正常渲染，指标与口径在文档中可追溯

## 3. 数据库迁移要求
1. 将 old-box 的表结构迁移为 Flyway 脚本：`sql/migrations/Vxxx__*.sql`
2. 字段命名可优化，但需提供 old→new 映射
3. 如果需要从旧库导入数据，提供一次性导入脚本（可选）

**验收标准**
- `docker compose up -d` + 启动 backend 后，Flyway 自动建表成功
- 基础数据（管理员账号、字典等）可通过 migration 或 seed 脚本初始化

## 4. 接口与错误码规范
- 后端返回结构统一（可沿用 old-box 的 `Result` 风格或项目既定规范）
- 常见错误：参数错误、未登录、无权限、业务校验失败、系统异常

**验收标准**
- 前端可根据错误码/结构进行统一提示与跳转

## 5. 前端迁移要求（页面清单）
old-box Vue2 页面（从 `old-box/myvue/src/views` 推断）：
- Login / Register
- Home / Echarts
- Box / Station / Map
- User / Role / Menu / Person
- Department
- Maintenance / MaintenanceHome
- Inspection / Detect / Fix / Check
- Files
- Course / Tax / Record / Components

迁移到 Vue3 后：
- 路由、菜单、权限控制与后端 RBAC 对齐
- 组件风格与 UI 框架（如已选 Element Plus）保持一致

**验收标准**
- 关键业务页面可用，CRUD 流程闭环

## 6. 非功能性要求
- 配置：本地/测试/生产环境可配置（DB、Redis、文件路径、CORS、JWT secret 等）
- 日志：关键操作记录（至少包含用户、操作、时间、对象）
- 安全：密码不可明文存储（BCrypt）

## 7. 需要 xjy 进一步确认的问题（否则需求不完整）
1. “必须迁移”的模块优先级：哪些是 MVP（第一期必须有），哪些可后置？
2. old-box 的数据库结构/字段定义是否以现有 `old-box/src/main/resources/mapper/*.xml` 与 entity 为准？是否有最新线上版本差异？
3. `Tax`、`Course`、`Station` 等模块的业务含义与是否保留
4. 是否需要数据导入（从旧库导入到新库），以及导入范围

---

## 附：old-box 后端主要代码入口（定位用）
- 登录：`old-box/src/main/java/com/xjy/shujia/controller/LoginController.java`
- 拦截器：`old-box/src/main/java/com/xjy/shujia/config/interceptor/JwtInterceptor.java`
- Mapper XML：`old-box/src/main/resources/mapper/*.xml`
- 配置：`old-box/src/main/resources/application.yaml`
