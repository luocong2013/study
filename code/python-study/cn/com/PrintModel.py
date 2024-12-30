print(r'\\\t\\')

a = "ABC"
b = a
a = "XYZ"
print(b)

print(11 / 5)
print(10 // 3)
print(11.4 // 3)

print(ord('C'))
print(ord('罗'))
print(chr(25991))
print('罗聪'.encode('UTF-8'))
print(b'\xe7\xbd\x97\xe8\x81\xaa'.decode('UTF-8'))
print(len('自然语言处理'))
print('%2d-%02d' % (8, 2))
print('%.2f' % 3.1415926)
print('Hello, {0}, 成绩提升了 {1:.1f}%'.format('小明', 17.125))

s1 = 72
s2 = 85
r = ((85 - 72) / s1) * 100
print('%2.1f%%' % r)
