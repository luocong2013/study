import numpy as np

a = np.array([0, 30, 45, 60, 90])
print("不同角度的正弦值：")
print(np.sin(a * np.pi / 180))

print("\n数组中角度的余弦值：")
print(np.cos(a * np.pi / 180))

print("\n数组中角度的正切值：")
print(np.tan(a * np.pi / 180))

b = np.arange(9, dtype=np.float_).reshape(3, 3)
print("\n第一个数组: ")
print(b)
print("\n第二个数组: ")
c = np.array([10, 10, 10])
print(c)

print("\n两个数组相加: ")
print(np.add(b, c))

print("\n两个数组相减: ")
print(np.subtract(b, c))

print("\n两个数组相乘: ")
print(np.multiply(b, c))

print("\n两个数组相除: ")
print(np.divide(b, c))
