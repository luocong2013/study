mylist = ["abcd", 789, 2.23, "runoob", 70.2]
tinylist = [123, "runoob"]

print(mylist)
print(len(mylist))
mylist.append('Admin')
mylist.insert(1, 'Jack')
mylist.pop(0)

print(mylist[0])
print(mylist[1:3])
print(mylist[2:])
print(tinylist * 2)
print(mylist + tinylist)

myLst = list(range(100))
# 前10个数
print(myLst[:10])
# 后10个数
print(myLst[-10:])
# 前10个数，每2个取1个
print(myLst[:10:2])
# 所有数每5个取1个
print(myLst[::5])
# 原样复制1个list
print(myLst[:])

print(tuple(range(10))[:3])

myStr = 'ABCDEF'
print(myStr[:3])
print(myStr[::2])


L = [
    ['Apple', 'Google', 'Microsoft'],
    ['Java', 'Python', 'Ruby', 'PHP'],
    ['Adam', 'Bart', 'Lisa']
]
# 打印Apple:
print(L[0][0])
# 打印Python:
print(L[1][1])
# 打印Lisa:
print(L[2][2])


# 列表生成式
print(list(range(1, 11)))
print([x * x for x in range(1, 11)])
print([x * x for x in range(1, 11) if x % 2 == 0])
print([m + n for m in 'ABC' for n in 'XYZ'])
L = ['Hello', 'World', 'IBM', 'Apple']
print([s.lower() for s in L])
