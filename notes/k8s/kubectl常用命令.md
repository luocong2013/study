# kubectl常用命令



### 查看节点

```shell
[root@dev-k8s-master1 ~]# kubectl get nodes
NAME            STATUS                     ROLES    AGE    VERSION
172.20.34.203   Ready,SchedulingDisabled   master   6d1h   v1.18.3
172.20.34.206   Ready                      node     6d1h   v1.18.3
172.20.34.207   Ready                      node     6d1h   v1.18.3
```

### 查看namespaces

```shell
[root@dev-k8s-master1 ~]# kubectl get namespaces
NAME              STATUS   AGE
business          Active   6d1h
data              Active   6d1h
default           Active   6d1h
```

### 查看pod

```shell
# 查看pod
[root@dev-k8s-master1 ~]# kubectl get pods
NAME    READY   STATUS    RESTARTS   AGE
nginx   1/1     Running   0          45h


# 查看pod的ip地址
[root@dev-k8s-master1 ~]# kubectl get pods -o wide
NAME    READY   STATUS    RESTARTS   AGE   IP            NODE            NOMINATED NODE   READINESS GATES
nginx   1/1     Running   0          45h   22.168.2.11   172.20.34.206   <none>           <none>


# 如果不知道pod部署在哪个命名空间
[root@dev-k8s-master1 ~]# kubectl get pod --all-namespaces | grep app
wechat        app                                             1/1     Running   0          33s
命名空间        pod


# 如何进入容器去看一些日志，例如我什么都不知道，只知道pod叫spring
[root@dev-k8s-master1 ~]# kubectl get pod --all-namespaces | grep spring
business        uaa-api-spring-server-app-66d768cdc-b48xx   2/2     Running             0          24h
business        uaa-api-spring-server-app-66d768cdc-crhdn   2/2     Running             0          24h


# 以json格式输出pod信息
[root@dev-k8s-master1 ~]# kubectl get pod -n business uaa-api-spring-server-app-66d768cdc-crhdn -o json

# 自定义查询列
[root@dev-k8s-master1 ~]# kubectl get pod -n business uaa-api-spring-server-app-66d768cdc-crhdn -o custom-columns='NAME:.metadata.name,RSRC:.metadata.resourceVersion'
NAME                         								RSRC
uaa-api-spring-server-app-66d768cdc-crhdn   1894223866


# 一个pod里面可以有多个容器，此shell是查看容器名字，这个是自己定义的，如果不知道可以这么查，一般情况下为pod名字的前缀
[root@dev-k8s-master1 ~]# kubectl get pod -n business uaa-api-spring-server-app-66d768cdc-crhdn -o custom-columns='CONTAINER:.spec.containers[*].name'
CONTAINER
fluent-bit,uaa-api-spring-server

# 一个pod里面可以有多个容器，此shell是查看容器名字（也可以这么查询）
[root@dev-k8s-master1 ~]# kubectl get pod -n business uaa-api-spring-server-app-66d768cdc-crhdn -o jsonpath='{.spec.containers[*].name}'
fluent-bit uaa-api-spring-server


# 进入k8s的一个pod容器
[root@dev-k8s-master1 ~]# kubectl exec -it -n business uaa-api-spring-server-app-66d768cdc-crhdn -c uaa-api-spring-server /bin/bash
[root@uaa-api-spring-server-app-66d768cdc-crhdn home]# ls
iso_tool  jdk1.8.0_221  logs  runtime  server  trace


# 直接进入k8s的一个容器
[root@dev-k8s-master1 ~]# name='uaa-api-spring-server' && kubectl exec -it -n business $(kubectl get po -n business | grep ${name} | awk '{print $1}') -c ${name} /bin/bash
[root@uaa-api-spring-server-app-66d768cdc-crhdn home]# ls
iso_tool  jdk1.8.0_221  logs  runtime  server  trace


# 如何把容器内的文件复制到本地
kubectl cp <some-namespace>/<some-pod>:/tmp/foo /tmp/bar -c Container name

[root@dev-k8s-master1 ~]# kubectl cp business/uaa-api-spring-server-app-66d768cdc-crhdn:/home/apache-tomcat-8.5.39/logs/app.2022-04-27.0.log.gz /home/app.2022-04-27.0.log.gz -c uaa-api-spring-server
tar: Removing leading `/' from member names

使用绝对路径会出现上面这个报错，这个问题本质上是kubectl的bug, kubectl cp 的时候, 是从 work dir 开始的, 目前不支持绝对路径.
issue 链接 https://github.com/kubernetes/kubernetes/issues/58692

# 我们可以这样，将想要cp的文件移动到 pod 中的 work dir 目录（当您在其上打开 bash 时自动打开的目录）
# https://github.com/kubernetes/kubernetes/issues/58692#issuecomment-380454694
[root@dev-k8s-master1 ~]# kubectl cp business/uaa-api-spring-server-app-66d768cdc-crhdn:app.2022-04-27.0.log.gz app.2022-04-27.0.log.gz -c uaa-api-spring-server
[root@dev-k8s-master1 ~]# ls
app.2022-04-27.0.log.gz
```

### 查看services

```shell
[root@dev-k8s-master1 ~]# kubectl get  services
NAME         TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   21.100.0.1       <none>        443/TCP   6d2h
nginx        ClusterIP   21.100.169.137   <none>        80/TCP    2d6h
```

### 如何查看有哪些资源类型及资源的缩写

```shell
[root@dev-k8s-master1 ~]# kubectl api-resources
NAME                              SHORTNAMES         APIGROUP                       NAMESPACED   KIND
bindings                                                                            true         Binding
componentstatuses                 cs                                                false        ComponentStatus
configmaps                        cm                                                true         ConfigMap
endpoints                         ep                                                true         Endpoints
events                            ev                                                true         Event
limitranges                       limits                                            true         LimitRange
namespaces                        ns                                                false        Namespace
nodes                             no                                                false        Node
persistentvolumeclaims            pvc                                               true         PersistentVolumeClaim
persistentvolumes                 pv                                                false        PersistentVolume
pods                              po                                                true         Pod


[root@dev-k8s-master1 ~]# kubectl get po
NAME    READY   STATUS    RESTARTS   AGE
nginx   1/1     Running   0          45h
```

### 将一个或多个本地端口转发到一个 pod

```shell
Forward one or more local ports to a pod.

 Use resource type/name such as deployment/mydeployment to select a pod. Resource type defaults to 'pod' if omitted.

 If there are multiple pods matching the criteria, a pod will be selected automatically. The forwarding session ends when the selected pod terminates, and a rerun of the command is needed to resume forwarding.

Examples:
  # Listen on ports 5000 and 6000 locally, forwarding data to/from ports 5000 and 6000 in the pod
  kubectl port-forward pod/mypod 5000 6000
  
  # Listen on ports 5000 and 6000 locally, forwarding data to/from ports 5000 and 6000 in a pod selected by the deployment
  kubectl port-forward deployment/mydeployment 5000 6000
  
  # Listen on port 8443 locally, forwarding to the targetPort of the service's port named "https" in a pod selected by the service
  kubectl port-forward service/myservice 8443:https
  
  # Listen on port 8888 locally, forwarding to 5000 in the pod
  kubectl port-forward pod/mypod 8888:5000
  
  # Listen on port 8888 on all addresses, forwarding to 5000 in the pod
  kubectl port-forward --address 0.0.0.0 pod/mypod 8888:5000
  
  # Listen on port 8888 on localhost and selected IP, forwarding to 5000 in the pod
  kubectl port-forward --address localhost,10.19.21.23 pod/mypod 8888:5000
  
  # Listen on a random port locally, forwarding to 5000 in the pod
  kubectl port-forward pod/mypod :5000

Options:
      --address=[localhost]: Addresses to listen on (comma separated). Only accepts IP addresses or localhost as a value. When localhost is supplied, kubectl will try to bind on both 127.0.0.1 and ::1 and will fail if neither of these addresses are available to bind.
      --pod-running-timeout=1m0s: The length of time (like 5s, 2m, or 3h, higher than zero) to wait until at least one pod is running

Usage:
  kubectl port-forward TYPE/NAME [options] [LOCAL_PORT:]REMOTE_PORT [...[LOCAL_PORT_N:]REMOTE_PORT_N]

Use "kubectl options" for a list of global command-line options (applies to all commands).

[root@dev-k8s-master1 ~]# kubectl port-forward -n business uaa-api-spring-server-app-66d768cdc-crhdn 7110:8080
Forwarding from 127.0.0.1:7110 -> 8080
Forwarding from [::1]:7110 -> 8080
Handling connection for 7110
```



