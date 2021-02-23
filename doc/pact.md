# Pact 测试
## 运行 consumer 测试
运行测试
```shell
mvn -Drevision=1.0.0-SNAPSHOT -DfailIfNoTests=false clean test -pl order -am
```
运行成功后会在 `target/pacts` 目录下生成契约文件
## 运行 provider 测试
本项目的 provider 为 storage 项目
### 远程获取契约
#### 部署 pact broker
使用 docker-compose 启动，将以下内容保存为 `docker-compose.yml` 文件
```yaml
version: "3"

services:
  postgres:
    image: postgres
    healthcheck:
      test: psql postgres --command "select 1" -U postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres-volume:/var/lib/postgresql/data
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
      POSTGRES_DB: postgres

  pact_broker:
    image: pactfoundation/pact-broker
    ports:
      - "8282:9292"
    depends_on:
      - postgres
    environment:
      PACT_BROKER_DATABASE_URL_ENVIRONMENT_VARIABLE_NAME: DATABASE_URL
      DATABASE_URL: "postgres://postgres:password@postgres/postgres"
      PACT_BROKER_DATABASE_USERNAME: postgres
      PACT_BROKER_DATABASE_PASSWORD: password
      PACT_BROKER_DATABASE_HOST: postgres
      PACT_BROKER_DATABASE_NAME: postgres
      PACT_BROKER_PORT: "9292"
      PACT_BROKER_LOG_LEVEL: INFO

volumes:
  postgres-volume:
```
启动 pact broker
```shell
docker-compose -f docker-compose.yml up -d
```
#### 上传契约
```shell
cd order
./mvnw -Drevision=1.0.0.SNAPSHOT pact:publish
```
#### 配置 provider
加上 `@PactBroker` 注解
```java
@PactBroker(
    host = "localhost",
    port = "8282",
    consumerVersionSelectors = {
        @VersionSelector(tag = "dev"),
        @VersionSelector(tag = "master"),
        @VersionSelector(tag = "test")
    }
)
public class PactStorageProviderTest {}
```

### 本地获取契约
#### 获取契约
将 consumer 中生成的契约文件，拷贝到 provider 的 `src/test/resources/pacts` 目录下
#### 配置 provider
加上 `@PactFolder` 注解
```java
@PactFolder("src/test/resources/pacts")
public class PactStorageProviderTest {}
```
