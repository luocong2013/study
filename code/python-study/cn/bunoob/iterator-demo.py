import sys

# 迭代器
# 迭代器有两个基本的方法：iter() 和 next()
list = [1, 2, 3, 4]
it = iter(list)
print('遍历list')
print(next(it))
print(next(it))

for x in it:
    print(x, end=' ')
print()

list2 = list.copy()
it2 = iter(list2)
print('遍历list2')
while True:
    try:
        print(next(it2))
    except StopIteration:
        sys.exit()
