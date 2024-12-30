# 字典是另一种可变容器模型，且可存储任意类型对象
# 字典的每个键值(key=>value)对用冒号(:)分割，每个对之间用逗号(,)分割，整个字典包括在花括号({})中 ,格式如下所示：
# d = {key1 : value1, key2 : value2}
# 键必须是唯一的，但值则不必
# 创建空字典 {}

dict0 = {'Name': 'Runoob', 'Age': 7, 'Class': 'First'}
dict1 = {'Alice': '2341', 'Beth': '9102', 'Cecil': '3258'}
dict2 = {'abc': 123, 98.6: 37}

# 获取值
print('dict1["Beth"]: ', dict1["Beth"])
print('dict2[98.6]: ', dict2[98.6])

# 向字典添加新内容的方法是增加新的键/值对，修改或删除已有键/值对如下实例:
dict1['Alice'] = 999
dict1['School'] = '菜鸟教程'
print('dict1["Alice"]: ', dict1['Alice'])
print("dict1['School']: ", dict1['School'])

# 删除键 'Alice'
del dict1['Alice']
print('dict1: ', dict1)

# 清空字典
dict1.clear()
print('dict1: ', dict1)

# 删除字典
del dict1
# print('dict1: ', dict1) # 报错

# 计算字典元素个数，即键的总数
print(len(dict2))

# 输出字典，以可打印的字符串表示
print(str(dict2))

# 如果键在字典dict里返回true，否则返回false
print('Name' in dict0)

# 以列表返回可遍历的(键, 值) 元组数组
print(dict0.items())

# 以列表返回一个字典所有的键
print(dict0.keys())

# 以列表返回字典中的所有值
print(dict0.values())

# 随机返回并删除字典中的一对键和值(一般删除末尾对)
print(dict0.popitem())
print(dict0)

# 构造函数 dict() 直接从键值对元组列表中构建字典
dict3 = dict([('sape', 4139), ('guido', 4127), ('jack', 4098)])
print(dict3)

# 使用推导式创建字典
dict4 = {x: x ** 2 for x in (2, 4, 6)}
print(dict4)

# 遍历
for k, v in dict3.items():
    print(k, v)

# 同时遍历两个或更多的序列，可以使用 zip() 组合
questions = ['name', 'quest', 'favorite color']
answers = ['lancelot', 'the holy grail', 'blue']
for k, v in zip(questions, answers):
    print('What is your {0}? It is {1}.'.format(k, v))
