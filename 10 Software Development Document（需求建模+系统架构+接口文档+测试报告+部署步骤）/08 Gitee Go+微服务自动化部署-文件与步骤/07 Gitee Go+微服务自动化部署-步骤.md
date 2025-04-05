1. Gitee Go——新建主机组——设置linux服务器

2. 新建流水线——选择代码仓库——设置触发条件

3. 编写编译脚本

   ```bash
   # 功能：打包
   # 参数说明：
   #    -Dmaven.test.skip=true：跳过单元测试
   #    -U：每次构建检查依赖更新，可避免缓存中快照版本依赖不更新问题，但会牺牲部分性能
   #    -e -X ：打印调试信息，定位疑难构建问题时建议使用此参数构建
   #    -B：以batch模式运行，可避免日志打印时出现ArrayIndexOutOfBoundsException异常
   # 使用场景：打包项目且不需要执行单元测试时使用
   mvn clean package -Dmaven.test.skip=true -U -e -X -B
   
   # 功能：自定义settings配置
   # 使用场景：如需手工指定settings.xml，可使用如下方式
   # 注意事项：如无需自定义settings配置且需要私有依赖仓库，可在该任务配置《私有仓库》处添加私有依赖
   # mvn -U clean package -s ./settings.xml
   
   ```

4. 编写部署脚本

   ```bash
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
       nohup java -jar "$jar" > "${jar%.jar}.out" 2>&1 &
   done
   
   echo "所有微服务已启动！"
   
   ```

5. 自动化运行