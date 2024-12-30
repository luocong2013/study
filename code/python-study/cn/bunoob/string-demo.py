var1 = 'Hello World!'
var2 = "Runoob"
var3 = 'abcdef罗聪'

# 通过索引获取字符串中字符
print("var1[0]: " + var1[0])

# 截取字符串中的一部分
print("var2[1:5]: " + var2[1:5])

# 转义字符
print("LuoC\tHH")

# 重复输出字符串
print('Hello' * 2)

# 成员运算符 - 如果字符串中包含给定的字符返回 True
print('H' in var1)

# 成员运算符 - 如果字符串中不包含给定的字符返回 True
print('M' not in var1)

# 原始字符串，没有转义特殊或不能打印的字符
print(r'\n')

# 字符串格式化
print("我叫 %s 今年 %d 岁!" % ('小明', 10))

# 将字符串的第一个字符转换为大写
print(var3.capitalize())

# 返回一个指定的宽度 width 居中的字符串，fillchar 为填充的字符，默认为空格
print(var3.center(20, '*'))

# 以 encoding 指定的编码格式编码字符串
print(var3.encode())

# Python3 中没有 decode 方法，但我们可以使用 bytes 对象的 decode() 方法来解码给定的 bytes 对象，这个 bytes 对象可以由 str.encode() 来编码返回
print(var3.encode().decode())

# 检查字符串是否以 obj 结束，如果beg 或者 end 指定则检查指定的范围内是否以 obj 结束，如果是，返回 True,否则返回 False
print(var3.endswith('聪', 0, len(var3)))

# 以指定字符串作为分隔符，将 seq 中所有的元素(的字符串表示)合并为一个新的字符串
print(var3.join('zx'))

# 返回字符串 str 中最大的字母
print(max(var3))

# 将字符串中的 str1 替换成 str2,如果 max 指定，则替换不超过 max 次
print(var3.replace('罗聪', '张三'))

# 删除字符串字符串末尾的空格
print(' ludu '.rstrip())
# 截掉字符串左边的空格或指定字符
print(' ludu '.lstrip())

# 返回一个原字符串右对齐,并使用fillchar(默认空格）填充至长度 width 的新字符串
print(var3.rjust(20, '&'))

# 以 str 为分隔符截取字符串，如果 num 有指定值，则仅截取 num 个子字符串
print(var3.split('e'))

# 将字符串中大写转换为小写，小写转换为大写
print(var1.swapcase())

# 返回"标题化"的字符串,就是说所有单词都是以大写开始，其余字母均为小写
print(var3.title())
