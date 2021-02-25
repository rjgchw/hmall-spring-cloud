## 生产打包

```shell
./mvnw -Drevision=1.0.0.RELEASE -Pprod clean verify
```

## 测试

```shell
# 测试所有
./mvnw -Drevision=1.0.0.SANPSHOT verify
# 测试 storage 模块
./mvnw -Drevision=1.0.0-SNAPSHOT -DfailIfNoTests=false clean test -pl storage -am
```


### 代码检查

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```shell
docker-compose -f src/main/docker/sonar.yml up -d
```

Then, run a Sonar analysis:

```shell
./mvnw -Drevision=1.0.0.RELEASE -Pprod clean verify sonar:sonar -Dsonar.host.url=http://localhost:9001
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```shell
./mvnw -Drevision=1.0.0.SANPSHOT initialize sonar:sonar -Dsonar.host.url=http://localhost:9001
```

## 开发环境
生成镜像
```shell
./mvnw -Drevision=1.0.0.RELEASE -Pprod verify jib:dockerBuild
```

启动服务 

```shell
docker-compose -f docker/docker-compose.yml up -d
```

## 中间件配置
* [seata 配置](doc/seata.md)
* [sentinel 配置](doc/sentinel.md)
  
## 测试配置
* [pact 配置](doc/pact.md)

## TODO list
*[x] 集成 sonar
* []docker一键启动服务
* []使用github action进行 ci
* []集成k8s
* []gatling
* []cucumber
* []基于消息队列的契约测试
* []swagger path 问题
