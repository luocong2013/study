## <center>CentOS 6 和 CentOS 7 升级系统内核</center>
### 一、CentOS 升级内核资源网站
* [ELRepo.org](http://elrepo.org/tiki/tiki-index.php)

### 二、CentOS 6.X 升级系统内核
1. 查看当前系统内核
> uname -r / uname -a

2. 载入公钥
> rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org

3. 根据本地CentOS版本下载对应的Elrepo
> rpm -Uvh http://www.elrepo.org/elrepo-release-6-8.el6.elrepo.noarch.rpm

4. 升级内核
   ① 升级为长期支持版本
   > yum --enablerepo=elrepo-kernel install kernel-lt -y

   ② 升级为线上最新版本
   > yum --enablerepo=elrepo-kernel install kernel-ml -y

5. 更换启动项文件
> vim /etc/grub.conf
> 把default=1修改为default=0

6. 重启
> reboot

### 三、CentOS 7.X 升级系统内核
1. 查看当前系统内核
> uname -r / uname -a

2. 载入公钥
> rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org

3. 根据本地CentOS版本下载对应的Elrepo
> rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-3.el7.elrepo.noarch.rpm

4. 载入elrepo-kernel元数据
> yum --disablerepo=\* --enablerepo=elrepo-kernel repolist

5. 查看可用的rpm包
> yum --disablerepo=\* --enablerepo=elrepo-kernel list kernel*

6. 安装最新版本的kernel
> yum --disablerepo=\* --enablerepo=elrepo-kernel install -y kernel-ml.x86_64

7. 设置GRUB默认的内核版本
> vim /etc/default/grub
> 设置 GRUB_DEFAULT=0

8. 重新创建内核配置
> grub2-mkconfig -o /boot/grub2/grub.cfg