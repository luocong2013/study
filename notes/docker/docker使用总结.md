## Docker使用总结

### 一、Docker常用命令

1. 登录

```
$ docker login
```

2. 标记image

```
$ docker tag [localImageRepository:tag | localImageId] username/repository:tag
```

3. push image

```
$ docker push username/repository:tag
```

4. pull image

```
$ docker pull imageName
```

5. build

```
$ docker build -t username/repository:tag .
$ docker build -t username/repository:tag -f dockerfile1 .
```

6. 进入容器

```
$ docker exec -it username/repository:tag /bin/bash
```

7. 启动容器

```
$ docker run -d -p 8868:8868 --name containerName username/repository:tag
```







二、使用阿里云Docker仓库

1. 登录

```
$ sudo docker login --username=luocong2013@outlook.com registry.cn-hangzhou.aliyuncs.com
```

2. 标记tag

```
$ sudo docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/luocong/image-sign:[镜像版本号]
```

3. push image

```
$ sudo docker push registry.cn-hangzhou.aliyuncs.com/luocong/image-sign:[镜像版本号]
```

4. pull image

```
$ sudo docker pull registry.cn-hangzhou.aliyuncs.com/luocong/image-sign:[镜像版本号]
```

5. build image

```
$ sudo docker build -t registry.cn-hangzhou.aliyuncs.com/luocong/image-sign:[镜像版本号] .
$ sudo docker build -t registry.cn-hangzhou.aliyuncs.com/luocong/image-sign:[镜像版本号] -f dockerfile1 .
```

6. 进入容器

```
$ sudo docker run -it registry.cn-hangzhou.aliyuncs.com/luocong/image-sign:[镜像版本号] /bin/bash
```

7. 启动容器

```
$ sudo docker run -d -p 8868:8868 --name containerName registry.cn-hangzhou.aliyuncs.com/luocong/image-sign:[镜像版本号]
```



