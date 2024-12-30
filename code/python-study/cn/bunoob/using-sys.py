import sys
# import cn.bunoob.support as support
# from cn.bunoob import *
from cn.bunoob import support

print('命令行参数如下：')

for i in sys.argv:
    print(i)

print('\n\nPython 路径为：', sys.path, '\n')

support.print_func('张三')
vec = support.fib(20)
print(vec)

# 内置的函数 dir() 可以找到模块内定义的所有名称
# 如果没有给定参数，那么 dir() 函数会罗列出当前定义的所有名称
print(dir(support))

a = 5
print(dir())
del a  # 删除变量名a
print(dir())
print(__file__)

support.func1()

support.func2()
