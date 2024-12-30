dict = {}
dict["one"] = "1 - 菜鸟教程"
dict[2] = "2 - 菜鸟工具"

print('one' in dict)

tinydict = {"name": "runoob", "code": 1, "site": "www.runoob.com"}

print(dict["one"])
print(dict[2])
print(tinydict)
print(tinydict.keys())
print(tinydict.values())

# 删除一个key
tinydict.pop('name')
print(tinydict)

a = 60
b = 13
print(a & b)
print(a | b)
print(a ^ b)
print(~a)

a = [1, 2, 3]
b = a
print(b is a)
print(b == a)
b = a[:]
print(b is a)
print(b == a)
