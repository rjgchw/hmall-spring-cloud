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
