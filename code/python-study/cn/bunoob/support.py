#!/usr/bin/python3
def print_func(par):
    print("Hello ", par)
    return


# 定义到 n  的斐波那契数列
def fib(n):
    result = []
    a, b = 0, 1
    while b < n:
        result.append(b)
        a, b = b, a + b
    return result


# 每个模块都有一个__name__属性，当其值是'__main__'时，表明该模块自身在运行，否则是被引入
if __name__ == '__main__':
    print('程序在自身运行')
else:
    print('程序在其他模块运行')


# 输出一个数的平方和立方
# 字符串对象的 rjust() 方法, 它可以将字符串靠右, 并在左边填充空格
def func1():
    for x in range(1, 102):
        print(repr(x).rjust(3), repr(x * x).rjust(5), end=' ')
        print(repr(x*x*x).rjust(7))


# 在 ':' 后传入一个整数, 可以保证该域至少有这么多的宽度。
def func2():
    for x in range(1, 11):
        print('{0:2d} {1:3d} {2:4d}'.format(x, x**2, x**3))
