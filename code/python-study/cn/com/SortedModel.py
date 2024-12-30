# 排序算法

numLst = [36, 5, -12, 9, -21]
print(sorted(numLst))

# 按指定的key进行排序，key指定的函数将作用于list的每一个元素上
# 并根据key函数返回的结果进行排序
print(sorted(numLst, key=abs))

strLst = ['bob', 'about', 'Zoo', 'Credit']
print(sorted(strLst))
# 字符串忽略大小写排序
print(sorted(strLst, key=str.lower))

# 进行反向排序，不必改动key函数，可以传入第三个参数reverse=True
print(sorted(strLst, key=str.lower, reverse=True))
