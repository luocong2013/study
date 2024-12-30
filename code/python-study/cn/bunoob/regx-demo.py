# 正则表达式
import re

# re.match() 函数
# re.match 尝试从字符串的起始位置匹配一个模式，如果不是起始位置匹配成功的话，match()就返回none
# 函数语法：re.match(pattern, string, flags=0)
# pattern: 匹配的正则表达式
# string: 要匹配的字符串
# flags: 标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等
# 匹配成功re.match方法返回一个匹配的对象，否则返回None
# 我们可以使用group(num) 或 groups() 匹配对象函数来获取匹配表达式

print(re.match('www', 'www.runoob.com').span())  # 在起始位置匹配
print(re.match('com', 'www.runoob.com'))  # 不在起始位置匹配

line = "Cats are smarter than dogs"
matchObj = re.match(r'(.*) are (.*?) .*', line, re.M | re.I)
if matchObj:
    print("matchObj.group() : ", matchObj.group())
    print("matchObj.group(1) : ", matchObj.group(1))
    print("matchObj.group(2) : ", matchObj.group(2))
else:
    print("No match!!")

# re.search() 函数
# re.search 扫描整个字符串并返回第一个成功的匹配
# 函数语法：re.search(pattern, string, flags=0)
# pattern: 匹配的正则表达式
# string: 要匹配的字符串
# flags: 标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等
# 匹配成功re.search方法返回一个匹配的对象，否则返回None
# 我们可以使用group(num) 或 groups() 匹配对象函数来获取匹配表达式
print(re.search('www', 'www.runoob.com').span())  # 在起始位置匹配
print(re.search('com', 'www.runoob.com').span())  # 不在起始位置匹配

searchObj = re.search(r'(.*) are (.*?) .*', line, re.M | re.I)
if matchObj:
    print("searchObj.group() : ", searchObj.group())
    print("searchObj.group(1) : ", searchObj.group(1))
    print("searchObj.group(2) : ", searchObj.group(2))
else:
    print("No search!!")

# re.match与re.search的区别
# re.match只匹配字符串的开始，如果字符串开始不符合正则表达式，则匹配失败，函数返回None；而re.search匹配整个字符串，直到找到一个匹配

# 检索和替换
# Python 的re模块提供了re.sub用于替换字符串中的匹配项
# 函数语法：re.sub(pattern, repl, string, count=0)
# pattern: 正则中的模式字符串
# repl: 替换的字符串，也可为一个函数
# string: 要被查找替换的原始字符串
# count: 模式匹配后替换的最大次数，默认 0 表示替换所有的匹配
phone = "2004-959-559 # 这是一个电话号码"
# 删除注释
num = re.sub(r'#.*$', "", phone)
print("电话号码 : ", num)

# 移除非数字的内容
num = re.sub(r'\D', "", phone)
print("电话号码 : ", num)


# repl 参数是一个函数
# 以下实例中将字符串中的匹配的数字乘于 2
def double(matched):
    value = int(matched.group('value'))
    return str(value * 2)


s = 'A23G4HFD567'
print(re.sub('(?P<value>\d+)', double, s))

# 正则表达式修饰符 - 可选标志
# 多个标志可以通过按位 OR(|) 它们来指定。如 re.I | re.M 被设置成 I 和 M 标志
# re.I  使匹配对大小写不敏感
# re.L  做本地化识别（locale-aware）匹配
# re.M  多行匹配，影响 ^ 和 $
# re.S  使 . 匹配包括换行在内的所有字符
# re.U  根据Unicode字符集解析字符。这个标志影响 \w, \W, \b, \B
# re.X  该标志通过给予你更灵活的格式以便你将正则表达式写得更易于理解


