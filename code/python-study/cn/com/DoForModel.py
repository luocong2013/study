from collections import Iterable

names = ['Michael', 'Bob', 'Tracy']
for name in names:
    print(name)

sum = 0
for x in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]:
    sum += x
print(sum)

print(list(range(5)))
sum = 0
for x in range(101):
    sum += x
print(sum)

sum = 0
n = 99
while n > 0:
    sum += n
    n -= 2
print(sum)

n = 1
while n <= 100:
    if n > 5:
        break
    print(n)
    n += 1
print('END')

n = 0
while n < 10:
    n += 1
    if n % 2 == 0:
        continue
    print(n)

d = {'a': 1, 'b': 2, 'c': 3}
for key in d:
    print(key)

for value in d.values():
    print(value)

for k, v in d.items():
    print(k, ':', v)

for ch in 'ABCDEF':
    print(ch)

# 判断元素是否是可迭代的
print(isinstance('abc', Iterable))
print(isinstance(123, Iterable))

for i, value in enumerate(['A', 'B', 'C']):
    print(i, ':', value)
