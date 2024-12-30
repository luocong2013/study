# 函数代码块以 def 关键词开头，后接函数标识符名称和圆括号 ()。
# 任何传入参数和自变量必须放在圆括号中间，圆括号之间可以用于定义参数。
# 函数的第一行语句可以选择性地使用文档字符串—用于存放函数说明。
# 函数内容以冒号起始，并且缩进。
# return [表达式] 结束函数，选择性地返回一个值给调用方。不带表达式的return相当于返回 None。
# Python 定义函数使用 def 关键字，一般格式如下：
# def 函数名（参数列表）:
#     函数体

# 定义函数
def printme(str):
    """打印任何传入的字符串"""
    print(str)
    return


printme('HELLO WORLD')


# python 传不可变对象实例. 如：整数、字符串、元组
def ChangeInt(a):
    a = 10
    return a


b = 2
ChangeInt(b)
printme(b)


# 传可变对象实例. 如：列表、字典
def changeme(myLst):
    """修改传入的列表"""
    myLst.append([1, 2, 3, 4])
    print("函数内取值: ", myLst)
    return


# 调用changeme函数
myLst = [99, 100, 101]
changeme(myLst)
print("函数外取值: ", myLst)


# 可变参数
def calc(*numbers):
    mysum = 0
    for n in numbers:
        mysum += n * n
    return mysum


print(calc(1, 3, 5, 7))
nums = [1, 2, 3]
print(calc(*nums))


# 关键字参数
def person(name, age, **kw):
    print('name: ', name, 'age: ', age, 'other: ', kw)


person(age=34, name='Michael')
person('Michael', 34, city='ChengDu', ip='192.168.1.1')
extra = {'city': 'ChengDu', 'job': 'Engineer'}
person('Jack', 24, **extra)


# 命名关键字参数
def person(name, age, *, city, job):
    print(name, age, city, job)


person('Jack', 25, city='ChengDu', job='Sutdent')


# 函数参数定义顺序：必选参数、默认参数、可变参数、命名关键字参数、关键字参数
# 如下：
def f1(a, b, c=0, *args, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw)


def f2(a, b, c=0, *, d, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'd =', d, 'kw =', kw)


args = (1, 2, 3, 4)
kw = {'d': 99, 'x': '#'}
f1(*args, **kw)
f1(1, 2, 3)

# python 使用 lambda 来创建匿名函数
# 所谓匿名，意即不再使用 def 语句这样标准的形式定义一个函数

# 求和函数
mySum = lambda arg1, arg2: arg1 + arg2

print('相加后的值为：', mySum(20, 34))

# 变量作用域
# Python中，程序的变量并不是在哪个位置都可以访问的，访问权限决定于这个变量是在哪里赋值的。
# 变量的作用域决定了在哪一部分程序可以访问哪个特定的变量名称。Python的作用域一共有4种，分别是：
# L （Local） 局部作用域
# E （Enclosing） 闭包函数外的函数中
# G （Global） 全局作用域
# B （Built - in） 内建作用域
# 以L – > E – > G – > B 的规则查找，即：在局部找不到，便会去局部外的局部找（例如闭包），再找不到就会去全局找，再者去内建中找。

x = int(2.9)  # 内建作用域
g_count = 0  # 全局作用域


def outer():
    o_count = 1  # 闭包函数外的函数中

    def inner():
        i_count = 2  # 局部作用域


# global 和 nonlocal关键字
# 当内部作用域想修改外部作用域的变量时，就要用到global和nonlocal关键字
num = 1


def fun1():
    global num  # 需要使用 global 关键字声明
    print(num)
    num = 123
    print(num)


fun1()
print(num)


# 如果要修改嵌套作用域（enclosing 作用域，外层非全局作用域）中的变量则需要 nonlocal 关键字
def outer1():
    num = 10
    print('嵌套作用域')
    def inner1():
        nonlocal num  # nonlocal关键字声明
        print(num)
        num = 100
        print(num)

    inner1()
    print(num)


outer1()
