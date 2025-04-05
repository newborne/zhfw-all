# 请在此输入部署脚本，如启动Java应用如下
# nohup java -jar test.jar &gt; nohup.out &amp;
#  echo 'Hello Gitee!'


cd /home/newborne/gitee_go/deploy

# 打印当前目录
echo "当前目录是: $(pwd)"
# 解压缩文件
echo "正在解压缩 $TAR_FILE ..."
tar -zxvf zhfw.tar.gz


# 启动所有微服务
echo "正在启动微服务 ..."

# 启动每个 JAR 包
for jar in ZHFW-*.jar; do
    echo "启动 $jar ..."
    java -jar "$jar" > "${jar%.jar}.out" 2>&1 &
done

echo "所有微服务已启动！"
