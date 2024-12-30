import numpy as np

# 迭代器对象numpy.nditer。 它是一个有效的多维迭代器对象，可以用于在数组上进行迭代
a = np.arange(0, 60, 5)
a = a.reshape(3, 4)
print("原始数组是：")
print(a)
print("原始数组的转置是：")
b = a.T
print(b)
print("\n以 C 风格顺序排序：")
c = b.copy(order='C')
print("修改后的数组是：")
for x in np.nditer(c):
    print(x, end=' ')
print("\n以 F 风格顺序排序：")
f = b.copy(order='F')
print("修改后的数组是：")
for x in np.nditer(f):
    print(x, end=' ')

