## <center>CentOS 7 常用命令总结</center>
### 一、CentOS 7 防火墙相关
#### 1. firewalld的基本使用
* 启动： systemctl start firewalld
* 查看状态： systemctl status firewalld
* 禁用： systemctl disable firewalld
* 停止： systemctl stop firewalld

#### 2. firewalld-cmd的基本使用
* 查看版本： firewall-cmd --version
* 查看帮助： firewall-cmd --help
* 显示状态： firewall-cmd --state
* 查看防火墙规则：firewall-cmd --list-all
* 查看所有打开的端口： firewall-cmd --zone=public --list-ports
* 更新防火墙规则： firewall-cmd --reload
* 查看区域信息:  firewall-cmd --get-active-zones
* 查看指定接口所属区域： firewall-cmd --get-zone-of-interface=eth0
* 拒绝所有包：firewall-cmd --panic-on
* 取消拒绝状态： firewall-cmd --panic-off
* 查看是否拒绝： firewall-cmd --query-panic

#### 3. 开启端口
* 添加：firewall-cmd --zone=public --add-port=80/tcp --permanent （--permanent永久生效，没有此参数重启后失效）
* 重新载入：firewall-cmd --reload
* 查看：firewall-cmd --zone=public --query-port=80/tcp
* 删除：firewall-cmd --zone=public --remove-port=80/tcp --permanent

#### 4. CentOS 7 切换为iptables防火墙
> 切换到iptables首先应该关掉默认的firewalld，然后安装iptables服务

* 关闭firewall
> service firewalld stop
> systemctl disable firewalld.service  #禁止firewall开机启动

* 安装iptables防火墙
> yum install iptables-services

* 编辑iptables防火墙配置
> vi /etc/sysconfig/iptables

* 开启防火墙
> service iptables start

* 设置防火墙开机启动
> systemctl enable iptables.service

### 二、CentOS 7 systemctl 命令
> systemctl是CentOS 7 的服务管理工具中主要的工具，它融合了之前CentOS 6 的service和chkconfig的功能于一体

* 启动一个服务：systemctl start firewalld.service
* 关闭一个服务：systemctl stop firewalld.service
* 重启一个服务：systemctl restart firewalld.service
* 显示一个服务的状态：systemctl status firewalld.service
* 查看所有已启动的服务：systemctl list-units --type=service
* 在开机时启用一个服务：systemctl enable firewalld.service
* 在开机时禁用一个服务：systemctl disable firewalld.service
* 查看服务是否开机启动：systemctl is-enabled firewalld.service
* 查看开机启动的服务列表：systemctl list-unit-files|grep enabled
* 查看启动失败的服务列表：systemctl --failed