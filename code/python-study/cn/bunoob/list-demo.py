from collections import deque

list1 = ['Google', 'Runoob', 1997, 2000, 1997, 2000]
list2 = [1, 2, 3]
list3 = [4, 5, 6]

print(list1)

# 列表元素个数
print(len(list1))

# 删除列表元素
del list1[2]
print(list1)

# 移除列表中的一个元素（默认最后一个元素），并且返回该元素的值
list1.pop(1)
print(list1)

# 移除列表中某个值的第一个匹配项, 如果没有这样的元素，就会返回一个错误
list1.remove(2000)
print(list1)

# 在列表末尾添加新的对象, 相当于 a[len(a):] = [x]
list1.append(1997)
print(list1)

# 将对象插入列表
list1.insert(1, 'Alibaba')
print(list1)

# 统计某个元素在列表中出现的次数
print(list1.count(1997))

# 从列表中找出某个值第一个匹配项的索引位置, 如果没有匹配的元素就会返回一个错误
print(list1.index(1997))

# 在列表末尾一次性追加另一个序列中的多个值, 相当于 a[len(a):] = L
list2.extend({7, 8, 9})
print(list2)

# 返回列表元素最大值
print(max(list2))

# 返回列表元素最小值
print(min(list2))

# 反向列表中元素
list3.reverse()
print('反向列表中元素: ', list3)

# 对原列表进行排序
list3.sort()
print('对list3原列表进行排序: ', list3)

# 清空列表, 等于del a[:]
list3.clear()
print('清空列表: ', list3)

# 复制列表, 浅复制，等于a[:]
list3 = list2.copy()
print('复制列表: ', list3)

# 删除实体变量
del list1

# +
print(list2 + list3)

# *
print(list2 * 3)

# 嵌套列表
print([list2, list3])

# 列表的使用
# ① 将列表当做堆栈使用
# 用 append() 方法可以把一个元素添加到堆栈顶。用不指定索引的 pop() 方法可以把一个元素从堆栈顶释放出来
stack = [3, 4, 5]
stack.append(6)
stack.append(7)
print(stack)
print(stack.pop(), stack)
print(stack.pop(), stack)

# ② 将列表当作队列使用
queue = deque(["Eric", "John", "Michael"])
queue.append("Terry")
queue.append("Graham")
print(queue)
print(queue.popleft(), queue)
print(queue.popleft(), queue)

# ③ 列表推导式
vec = [2, 4, 6]
vec1 = [3 * x for x in vec]
print(vec1)

vec2 = [[x, x ** 2] for x in vec]
print(vec2)

# 这里我们对序列里每一个元素逐个调用某方法
freshfruit = ['  banana', '  loganberry ', 'passion fruit  ']
vec3 = [weapon.strip() for weapon in freshfruit]
print(vec3)

# 我们可以用 if 子句作为过滤器
vec4 = [3 * x for x in vec if x > 3]
print(vec4)

dec = [4, 3, -9]
dec1 = [x * y for x in vec for y in dec]
print(dec1)

dec2 = [vec1[i] * dec[i] for i in range(len(vec))]
print(dec2)

# 同时遍历两个或更多的序列，可以使用 zip() 组合
questions = ['name', 'quest', 'favorite color']
answers = ['lancelot', 'the holy grail', 'blue']
for k, v in zip(questions, answers):
    print('What is your {0}? It is {1}.'.format(k, v))
