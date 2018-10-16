# WCP
###### 微信中控平台

## 1. 项目背景

## 2. 如何启动

1. 打包
```cmd
./mvnw clean package
```
2. 启动
```cmd
java -jar wcp-0.0.1-SNAPSHOT.jar
```

## 2. 领域模型

1. Manager
2. Role
3. Account
4. User
5. Tag

Manager只有一个Role，Role管理多个Account，Account被多个User关注，User具备多个Tag。