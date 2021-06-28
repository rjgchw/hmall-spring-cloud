## 打包成镜像
先 [下载](https://github.com/alibaba/Sentinel/releases) jar 文件，然后将 jar 文件放到 `sentinel-build` 目录下
```
$ docker build -t sentinel-dashboard:1.8.1 .
```
## 限流
在 `nacos` 中的 `dev` 命名空间中配置一下信息:
```
data-id = gateway-rule.json
group=gateway
[{
    "resource": "/api/account",
    "limitApp": "default",
    "grade": 1,
    "count": 1,
    "strategy": 0,
    "controlBehavior": 0,
    "clusterMode": false
}]
```
