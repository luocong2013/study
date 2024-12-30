import numpy as np

a = np.arange(55).reshape(5, 11)
print(a)

print(a.ndim)

print(a.shape)

print(a.size)

print(a.dtype)

print(a.itemsize)

print(type(a))


b = np.array([[1., 2, -1, 2, 1],
              [2, 4, 1, -2, 3],
              [3, 6, 2, -6, 5]])
print(b)
print(b.ndim)
print(b.shape)
