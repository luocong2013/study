import numpy as np

print("将列表转换为 ndarray ")
x = [1, 2, 3]
a = np.asarray(x, np.float16)
print(a)

print("来自元组的 ndarray")
x = (1, 2, 3)
b = np.asarray(x)
print(b)

print("来自元组列表的 ndarray")
x = [(1, 2, 3), (4, 5)]
c = np.asarray(x)
print(c)
