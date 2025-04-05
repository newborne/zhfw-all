#!/bin/bash

# 进入部署目录
cd /home/newborne/gitee_go/deploy

# 打印当前目录
echo "当前目录是: $(pwd)"

# 停止所有微服务
echo "正在停止微服务 ..."

# 停止每个 JAR 包
for jar in ZHFW-*.jar; do
    echo "停止 $jar ..."
    pid=$(pgrep -f "$jar")
    if [ -n "$pid" ]; then
        kill "$pid"
    fi
done

echo "所有微服务已停止!"
