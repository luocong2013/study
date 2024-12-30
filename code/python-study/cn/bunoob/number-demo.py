import math
import random

var1 = 1
var2 = 10
print(var1)
print(var2)
# 删除对象
del var1
# print(var1) # 报错
print(var2)

# 幂运算
print(5 ** 2)
print(math.pow(5, 2))

var1 = 10
print((var1 > var2) - (var1 < var2))

# e的x次幂
print(math.exp(1))

# 向上取整
print(math.ceil(4.1))

# 向下取整
print(math.floor(8.9))

# 四舍五入
print(round(4.498684, 3))

# 绝对值
print(math.fabs(-10))

# log 以 2 为底
print(math.log2(8))

# log 以 e 为底
print(math.log(math.e))

# log 以 10 为底
print(math.log10(100))

# log 以 x 为底
print(math.log(81, 3))

# 平方根
print(math.sqrt(121))

# 随机数函数
# 从0到9中随机挑选一个整数
print(random.choice(range(10)))

# 三角函数
print(math.acos(0.7))
print(math.sin(math.pi / 6))
print(math.cos(math.pi / 3))

# 将弧度转换为角度
print(math.degrees(math.pi / 2))

# 将角度转换为弧度
print(math.radians(90))
