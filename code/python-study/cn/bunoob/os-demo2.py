import os


# 显示所有视频格式文件，mp4、avi、rmvb

def search_file(start_dir, target):
    os.chdir(start_dir)  # 改变当前工作目录

    for each_file in os.listdir(os.curdir):
        ext = os.path.splitext(each_file)[1]
        if ext in target:
            vedio_list.append(os.getcwd() + os.sep + each_file + os.linesep)
        if os.path.isdir(each_file):
            search_file(each_file, target)  # 递归调用
            os.chdir(os.pardir)  # 递归调用后切记返回上一层目录


start_dir = input('请输入待查找的初始目录：')

target = ['.mp4', '.avi', '.rmvb']
vedio_list = []
search_file(start_dir, target)

f = open('D:\\mywork\\pycharm_workspace\\FirstPython\\temp\\vedioList.txt', 'w')
f.writelines(vedio_list)
f.close()
