import os
import os.path

# 获取指定目录及其子目录下的 py 文件路径
# 说明：L 用于存储找到的 py 文件路径 get_py 函数，递归查找并存储 py 文件路径于 L

L = []


def get_py(path, L):
    fileLst = os.listdir(path)  # 获取path目录下所有文件
    for filename in fileLst:
        pathTmp = os.path.join(path, filename)  # 获取path与filename组合后的路径
        if os.path.isdir(pathTmp):  # 如果是目录
            get_py(pathTmp, L)  # 则递归查找
        elif filename[-3:].upper() == '.PY':  # 不是目录,则比较后缀名
            L.append(pathTmp)


path = input('请输入路径：').strip()
get_py(path, L)
print('在{0:s}目录及其字目录下找到{1:d}个py文件\n分别为：\n'.format(path, len(L)))
for filepath in L:
    print(filepath + '\n')
