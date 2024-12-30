import pickle, pprint

# 读和写文件
# open() 将会返回一个 file 对象
# open(filename, mode)
# filename：filename 变量是一个包含了你要访问的文件名称的字符串值
# mode：mode决定了打开文件的模式：只读，写入，追加等。这个参数是非强制的，默认文件访问模式为只读(r)。
# 所有可取值见如下的完全列表：
# r   以只读方式打开文件。文件的指针将会放在文件的开头。这是默认模式
# rb  以二进制格式打开一个文件用于只读。文件指针将会放在文件的开头
# r+  打开一个文件用于读写。文件指针将会放在文件的开头
# rb+ 以二进制格式打开一个文件用于读写。文件指针将会放在文件的开头
# w   打开一个文件只用于写入。如果该文件已存在则将其覆盖。如果该文件不存在，创建新文件
# wb  以二进制格式打开一个文件只用于写入。如果该文件已存在则将其覆盖。如果该文件不存在，创建新文件
# w+  打开一个文件用于读写。如果该文件已存在则将其覆盖。如果该文件不存在，创建新文件
# wb+ 以二进制格式打开一个文件用于读写。如果该文件已存在则将其覆盖。如果该文件不存在，创建新文件
# a   打开一个文件用于追加。如果该文件已存在，文件指针将会放在文件的结尾。也就是说，新的内容将会被写入到已有内容之后。如果该文件不存在，创建新文件进行写入
# ab  以二进制格式打开一个文件用于追加。如果该文件已存在，文件指针将会放在文件的结尾。也就是说，新的内容将会被写入到已有内容之后。如果该文件不存在，创建新文件进行写入
# a+  打开一个文件用于读写。如果该文件已存在，文件指针将会放在文件的结尾。文件打开时会是追加模式。如果该文件不存在，创建新文件用于读写
# ab+ 以二进制格式打开一个文件用于追加。如果该文件已存在，文件指针将会放在文件的结尾。如果该文件不存在，创建新文件用于读写

# 打开一个文件
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo.txt", "w")
# f.write(string) 将 string 写入到文件中, 然后返回写入的字符数
num = f.write("Python 是一个非常好的语言。\n是的，的确非常好!!\n")
# f.flush() 刷新文件内部缓冲，直接把内部缓冲区的数据立刻写入文件, 而不是被动的等待输出缓冲区写入
f.flush()
print(num)
# 关闭打开的文件
f.close()

# f.read()
# 为了读取一个文件的内容，调用 f.read(size), 这将读取一定数目的数据, 然后作为字符串或字节对象返回
# size 是一个可选的数字类型的参数。 当 size 被忽略了或者为负, 那么该文件的所有内容都将被读取并且返回
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo.txt", "r")
mystr = f.read()
print(mystr)
f.close()

# f.readline()
# 会从文件中读取单独的一行。换行符为 '\n'。f.readline() 如果返回一个空字符串, 说明已经已经读取到最后一行
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo.txt", "r")
mystr2 = f.readline()
print(mystr2)
f.close()

# f.readlines()
# 将返回该文件中包含的所有行,如果设置可选参数 sizehint, 则读取指定长度的字节, 并且将这些字节按行分割
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo.txt", "r")
myLst = f.readlines()
print(myLst)
f.close()

# 迭代一个文件对象然后读取每行
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo.txt", "r")
for line in f:
    print(line, end='')
f.close()

# 如果要写入一些不是字符串的东西, 那么将需要先进行转换
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo1.txt", "w")
value = ('www.runoob.com', 14)
s = str(value)
f.write(s)
f.close()

# f.tell() 返回文件对象当前所处的位置, 它是从文件开头开始算起的字节数

# f.seek() 如果要改变文件当前的位置, 可以使用 f.seek(offset, from_what) 函数
# offset 移动字符数
# from_what 的值, 如果是 0 表示开头, 如果是 1 表示当前位置, 2 表示文件的结尾，默认值为 0，即文件开头
# 如：seek(x,0) ： 从起始位置即文件首行首字符开始移动 x 个字符
f = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo.txt", "ab+")
f.write(b'0123456789abcdef')
f.seek(5)  # 移动到文件的第六个字节
print(f.read(1))
f.seek(-3, 2)  # 移动到文件的倒数第三字节
print(f.read(1))
f.close()

# 预定义的清理行为
# 关键词 with 语句就可以保证诸如文件之类的对象在使用完之后一定会正确的执行他的清理方法
with open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\foo.txt", "r") as f:
    for line in f:
        print(line, end='')
print()

# pickle 模块
# 通过pickle模块的序列化操作我们能够将程序中运行的对象信息保存到文件中去，永久存储
# 通过pickle模块的反序列化操作，我们能够从文件中创建上一次程序保存的对象
# 基本接口: pickle.dump(obj, file, [,protocol])
# 有了 pickle 这个对象, 就能对 file 以读取的形式打开: x = pickle.load(file)
# 注解：从 file 中读取一个字符串，并将它重构为原来的python对象

# 写入数据
data1 = {'a': [1, 2.0, 3, 4 + 6j],
         'b': ('string', u'Unicode string'),
         'c': None}
selfref_list = [1, 2, 3]
selfref_list.append(selfref_list)
output = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\data.pk1", 'wb')
pickle.dump(data1, output)
pickle.dump(selfref_list, output, -1)
output.close()

# 读取数据
pk1_file = open("D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\data.pk1", 'rb')
data1 = pickle.load(pk1_file)
pprint.pprint(data1)

data2 = pickle.load(pk1_file)
pprint.pprint(data2)
pk1_file.close()
