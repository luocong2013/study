tup1 = ('Google', 'Runoob', 1997, 2000)
tup2 = (1, 2, 3, 4, 5)
tup3 = "a", "b", "c", "d"

# 创建空元组
tup4 = ()

# 元组中只包含一个元素时，需要在元素后面添加逗号，否则括号会被当作运算符使用
tup5 = (50,)
print(type(tup5))

print('tup1[0]: ', tup1[0])
print('tup2[1:5]', tup2[1:5])

# 元组中的元素值是不允许修改的，但我们可以对元组进行连接组合
tup4 = tup1 + tup2
print(tup4)
print(tup1 * 3)

# 计算元组元素个数
print('tup1个数: ', len(tup1))

# 返回元组中元素最大值
print('tup2中最大值为: ', max(tup2))

# 返回元组中元素最小值
print('tup2中最小值: ', min(tup2))

# 元组中的元素值是不允许删除的，但我们可以使用del语句来删除整个元组
print(tup1)
del tup1
# print(tup1) # 报错

# 将列表转换为元组
list1 = ['Google', 'Taobao', 'Runoob', 'Baidu']
tup6 = tuple(list1)
print(tup6)
