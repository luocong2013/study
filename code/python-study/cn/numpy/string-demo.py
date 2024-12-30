import numpy as np

print("连接两个字符串：")
print(np.char.add(['hello'], [' xyz']))
print(np.char.add(['hello', 'hi'], [' abc', ' xyz']))

print("\n函数执行多重连接: ")
print(np.char.multiply('Hello ', 3))

print("\n返回所需宽度的数组，以便输入字符串位于中心: ")
print(np.char.center('hello', 20, fillchar='*'))

print("\n函数返回字符串的副本，其中第一个字母大写: ")
print(np.char.capitalize('hello world'))

print("\n返回输入字符串的按元素标题转换版本，其中每个单词的首字母都大写: ")
print(np.char.title('hello how are you?'))

print("\n返回输入字符串中的单词列表。 默认情况下，空格用作分隔符: ")
print(np.char.split('hello how are you?'))
print(np.char.split('YiibaiPoint,Hyderabad,Telangana', sep=','))

# '\n'，'\r'，'\r\n'都会用作换行符
print("\n函数返回数组中元素的单词列表，以换行符分割: ")
print(np.char.splitlines('hello\nhow are you?'))
print(np.char.splitlines('hello\rhow are you?'))

print("\n函数返回数组的副本，其中元素移除了开头或结尾处的特定字符: ")
print(np.char.strip('ashok arora', 'a'))
print(np.char.strip(['arora', 'admin', 'java'], 'a'))

print("\n单个字符由特定的分隔符连接: ")
print(np.char.join(':', 'dmy'))
print(np.char.join([':', '-'], ['dmy', 'ymd']))

print("\n所有字符序列的出现位置都被另一个给定的字符序列取代: ")
print(np.char.replace('He is a good boy', 'is', 'was'))

print("\n编码， 默认编码是utf_8: ")
a = np.char.encode('罗聪')
print(a)
print(np.char.decode(a))
