# 避免直接侵入原函数修改，但是生效需要再次执行函数
import time


def deco():
    start = time.time()
    func()
    end = time.time()
    msecs = (end - start) * 1000
    print('time is {} ms'.format(msecs))


def func():
    print("hello")
    time.sleep(1)
    print('world')


if __name__ == '__main__':
    deco()
