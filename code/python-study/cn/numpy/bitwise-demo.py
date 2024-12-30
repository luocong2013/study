import numpy as np

print("13 和 17 的二进制形式：")
a, b = 13, 17

print(bin(a), bin(b))

print("13 和 17 的位与：")
print(np.bitwise_and(a, b))

print("13 和 17 的位或：")
print(np.bitwise_or(a, b))

print("13 的位反转，其中 ndarray 的 dtype 是 uint8：")
print(np.invert(np.array([13], dtype=np.uint8)))

print("13 的二进制表示：")
print(np.binary_repr(13, width=8))

print("242 的二进制表示：")
print(np.binary_repr(242, width=8))

print("将 10 左移两位：")
print(np.left_shift(10, 2))

print("将 40 右移两位：")
print(np.right_shift(40, 2))
