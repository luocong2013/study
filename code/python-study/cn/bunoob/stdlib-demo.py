import os
import shutil
import glob
import re
import random
from urllib.request import urlopen
from datetime import date
import zlib
from timeit import Timer

print(os.getcwd())  # 返回当前的工作目录

os.chdir("D:/mywork/pycharm_workspace/FirstPython/temp")  # 修改当前的工作目录
print(os.getcwd())

os.system("java -version")  # 执行系统命令

print(dir(os))
# print(help(os))

# 针对日常的文件和目录管理任务，:mod:shutil 模块提供了一个易于使用的高级接口
shutil.copyfile("foo1.txt", "foo1_bak.txt")

# glob模块提供了一个函数用于从目录通配符搜索中生成文件列表
os.chdir("D:/mywork/pycharm_workspace/FirstPython/cn/bunoob")
print(glob.glob('*.py'))

# 字符串正则匹配
regx = r'\bf[a-z]*'
pattern = re.compile(regx)
myLst = re.findall(pattern, 'which foot or hand fell fastest')
print(myLst)

# 生成随机数的工具
print(random.choice(['apple', 'pear', 'banana']))
print(random.sample(range(100), 10))
print(random.random())
print(random.randrange(6))

# 访问互联网以及处理网络通信协议
# 其中最简单的两个是用于处理从 urls 接收的数据的 urllib.request 以及用于发送电子邮件的 smtplib
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\cuit.html", "w", encoding='UTF-8')
for line in urlopen('http://www.cuit.edu.cn/'):
    line = line.decode('UTF-8')
    f.write(line + os.linesep)

# 日期和时间
now = date.today()
print(now)
strNow = now.strftime("%m-%d-%y. %d %b %Y is a %A on the %d day of %B.")
print(strNow)

birthday = date(1994, 10, 20)
age = now - birthday
print(age.days)

# 数据压缩
s = b'witch which has which witches wrist watch'
print(len(s))
t = zlib.compress(s)
print(len(t))
print(zlib.decompress(t))
print(zlib.crc32(s))

# 性能度量
lt1 = Timer('t=a; a=b; b=t', 'a=1;b=2').timeit()
print(lt1)
lt2 = Timer('a,b = b, a', 'a=1; b=2').timeit()
print(lt2)
