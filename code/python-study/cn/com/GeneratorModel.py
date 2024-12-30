from collections import Iterable

L = [x * x for x in range(10)]
print(L)

# 创建一个generator
g = (x * x for x in range(10))
# generator是可迭代的
print(isinstance(g, Iterable))
for n in g:
    print(n)


# 斐波拉契数列（Fibonacci）
def fib(max):
    n, a, b = 0, 0, 1
    while n < max:
        # print(b)
        # 定义generator的另一种方法
        yield b
        a, b = b, a + b
        n += 1
    return 'done'


print(fib(10))
for n in fib(10):
    print(n)
