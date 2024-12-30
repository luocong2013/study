import math


def my_abs(x):
    if not isinstance(x, (int, float)):
        raise TypeError('bad operand type')
    if x >= 0:
        return x
    else:
        return -x


print(my_abs(-9))


def move(x, y, step, angle=0):
    nx = x + step * math.cos(angle)
    ny = y - step * math.sin(angle)
    return nx, ny


print(move(100, 100, 60, math.pi / 6))


def power(x, n=2):
    s = 1
    while n > 0:
        n -= 1
        s *= x
    return s


print(power(2))


# list tuple set
def calc(numbers):
    mysum = 0
    for n in numbers:
        mysum += n * n
    return mysum


print(calc([1, 3, 5, 7]))
print(calc((1, 3, 5, 7)))
print(calc({1, 3, 5, 7}))


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


print(person('Michael', 34))
print(person('Michael', 34, city='ChengDu', ip='192.168.1.1'))
extra = {'city': 'ChengDu', 'job': 'Engineer'}
print(person('Jack', 24, **extra))


# 命名关键字参数
def person(name, age, *, city, job):
    print(name, age, city, job)


print(person('Jack', 25, city='ChengDu', job='Sutdent'))


# 函数参数定义顺序：必选参数、默认参数、可变参数、命名关键字参数、关键字参数
# 如下：
def f1(a, b, c=0, *args, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw)


def f2(a, b, c=0, *, d, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'd =', d, 'kw =', kw)


args = (1, 2, 3, 4)
kw = {'d': 99, 'x': '#'}
print(f1(*args, **kw))
print(f1(1, 2, 3))

