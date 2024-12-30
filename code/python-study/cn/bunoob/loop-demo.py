# while 循环
n = 100
sun = 0
count = 1
while count <= n:
    sun += count
    count += 1
print('1 到 %d 之和为：%d' % (n, sun))

# while...else
# 循环语句可以有 else 子句，它在穷尽列表(以for循环)或条件变为 false (以while循环)导致循环终止时被执行,但循环被break终止时不执行
count = 0
while count < 5:
    print(count, ' 小于 5')
    count += 1
else:
    print(count, ' 大于或等于5')

# for 循环
languages = ["C", "C++", "Perl", "Python"]
for x in languages:
    print(x, end=', ')
print()

# for...else 如果从 for 或 while 循环中终止，任何对应的循环 else 块将不执行
sites = ["Baidu", "Google", "Runoob", "Taobao"]
for site in sites:
    if site == 'Runoob':
        print('菜鸟教程')
        break
    print('循环数据：', site)
else:
    print('没有循环数据')
print('完成循环！')

# range() 函数
for i in range(5):
    print(i, end='\t')
print()
for i in range(5, 9):
    print(i, end='\t')
print()
for i in range(0, 10, 3):
    print(i, end='\t')
print()

# 查找质数
for n in range(2, 100):
    for x in range(2, n):
        if n % x == 0:
            print(n, '等于', x, '*', n // x)
            break
    else:
        print(n, ' 是质数')

# 生成列表中包含 10个 ok
test = ['ok' for _ in range(10)]
print(test)
