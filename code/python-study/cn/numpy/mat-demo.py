import numpy.matlib
import numpy as np

print("matlib.empty(): ")
a = np.matlib.empty((2, 2))
print(a)

print("\nmatlib.zeros(): ")
b = np.matlib.zeros((2, 2))
print(b)

print("\nmatlib.ones(): ")
c = np.matlib.ones((2, 2))
print(c)

print("\nmatlib.eye(): ")
d = np.matlib.eye(n=3, M=4, k=0, dtype=float)
print(d)

print("\nmatlib.identity(): ")
e = np.matlib.identity(5, dtype=float)
print(e)

print("\nmatlib.rand(): ")
f = np.matlib.rand(3, 3)
print(f)
