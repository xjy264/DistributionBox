#!/bin/bash

# 配色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}    配电箱管理系统 - 启动脚本          ${NC}"
echo -e "${GREEN}========================================${NC}"

# 进入项目根目录
cd "$(dirname "$0")"

# 检查 Docker
if ! command -v docker &> /dev/null; then
    echo -e "${RED}[错误] 未找到 Docker，请先安装 Docker${NC}"
    exit 1
fi

# 设置 Java 21
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home
if [ ! -d "$JAVA_HOME" ]; then
    echo -e "${RED}[错误] 未找到 Java 21，请先安装 Temurin JDK 21${NC}"
    exit 1
fi
echo -e "${GREEN}[信息] 使用 Java 21: $JAVA_HOME${NC}"

# 启动 MySQL 和 Redis 容器
echo -e "${YELLOW}[1/2] 启动 MySQL 和 Redis 容器...${NC}"
docker compose up -d

# 等待 MySQL 就绪
echo -e "${YELLOW}[信息] 等待 MySQL 就绪...${NC}"
for i in {1..30}; do
    if docker exec distributionbox-mysql mysqladmin ping -h localhost -u root -pxjy12345 &> /dev/null; then
        echo -e "${GREEN}[信息] MySQL 已就绪${NC}"
        break
    fi
    if [ $i -eq 30 ]; then
        echo -e "${RED}[错误] MySQL 启动超时${NC}"
        exit 1
    fi
    sleep 2
done

# 等待 Redis 就绪
echo -e "${YELLOW}[信息] 等待 Redis 就绪...${NC}"
for i in {1..15}; do
    if docker exec distributionbox-redis redis-cli ping &> /dev/null; then
        echo -e "${GREEN}[信息] Redis 已就绪${NC}"
        break
    fi
    if [ $i -eq 15 ]; then
        echo -e "${RED}[错误] Redis 启动超时${NC}"
        exit 1
    fi
    sleep 1
done

# 杀死占用 8888 端口的进程
if lsof -ti:8888 &> /dev/null; then
    echo -e "${YELLOW}[信息] 杀死占用 8888 端口的进程...${NC}"
    lsof -ti:8888 | xargs kill -9 2>/dev/null
    sleep 1
    echo -e "${GREEN}[信息] 端口 8888 已释放${NC}"
fi

# 启动 Spring Boot 后端
echo -e "${YELLOW}[2/2] 启动后端服务 (端口: 8888)...${NC}"
echo ""

# 进入 backend 目录
cd backend

# 使用 Maven Wrapper 或系统 Maven
if [ -f "./mvnw" ]; then
    ./mvnw spring-boot:run
elif command -v mvn &> /dev/null; then
    mvn spring-boot:run
else
    echo -e "${RED}[错误] 未找到 Maven，请安装 Maven 或使用 mvnw${NC}"
    exit 1
fi
