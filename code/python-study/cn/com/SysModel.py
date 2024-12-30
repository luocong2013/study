import sys

print("===============Python import mode==================")
print("命令行参数为：")
for i in sys.argv:
    print(i)
print("\npython 路径为", sys.path)


class A:
    pass


class B(A):
    pass


print(isinstance(A(), A))
print(type(A()) == A)
print(isinstance(B(), A))
print(type(B()) == A)
