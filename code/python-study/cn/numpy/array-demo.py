import numpy as np

a = np.array([1.2, 2, 3])
print(a)

b = np.array([[1, 2], [3, 4]])
print(b)

# 最小维度
c = np.array([1, 2, 3, 4, 5, 6], ndmin=2)
print(c)

d = np.array([1, 2, 3], dtype=complex)
print(d)

print("NumPy - 数组属性")
e = np.array([[1, 2, 3], [4, 5, 6]])
print(e.shape)
e.shape = (3, 2)
print(e)

print("arange")
g = np.arange(24)
print(g)
print(g.ndim)
h = g.reshape(2, 4, 3)
print(h)
print(h.ndim)

print("NumPy - empty")
i = np.empty((3, 2), dtype=np.int16)
print(i)

j = np.zeros(5, np.int16, order='')
print(j)

k = np.ones((2, 5), dtype=np.float64)
print(k)
