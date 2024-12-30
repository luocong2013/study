import numpy.matlib
import numpy as np

print("两个数组的点积: ")
a = np.array([[1, 2], [3, 4]])
b = np.array([[11, 12], [13, 14]])
print(np.dot(a, b))

print("\n两个向量的点积: ")
print(np.vdot(a, b))

print("\n一维数组的向量内积。 对于更高的维度，它返回最后一个轴上的和的乘积: ")
c = np.array([1, 2, 3])
d = np.array([0, 1, 0])
print(np.inner(c, d))
print(np.inner(a, b))

print("\n两个数组的矩阵乘积: ")
a = [[1, 0], [0, 1]]
b = [1, 2]
print(np.matmul(a, b))
print(np.matmul(b, a))
a = np.arange(8).reshape(2, 2, 2)
b = np.arange(4).reshape(2, 2)
print(np.matmul(a, b))

print("\n计算输入矩阵的行列式: ")
a = np.array([[1, 2], [3, 4]])
print(np.linalg.det(a))
b = np.array([[6, 1, 1], [4, -2, 5], [2, 8, 7]])
print(np.linalg.det(b))

print("\n矩阵的逆: ")
x = np.array([[1, 2], [3, 4]])
y = np.linalg.inv(x)
print(x)
print(y)
print(np.dot(x, y))

print("\n求解线性方程组: ")
a = np.array([[1, 1, 1], [0, 2, 5], [2, 5, -1]])
print("矩阵 a: ")
print(a)
ainv = np.linalg.inv(a)

print("a 的逆: ")
print(ainv)

print("矩阵 b: ")
b = np.array([[6], [-4], [27]])
print(b)

print("计算：a^(-1) * b")
x = np.linalg.solve(a, b)
print(x)
print("结果也可用: np.dot(ainv, b)")
print(np.dot(ainv, b))
print(np.matmul(ainv, b))
