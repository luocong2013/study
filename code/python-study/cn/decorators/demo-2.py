# 既不需要侵入，也不需要函数重复执行
import time


def deco(function):
    def wrapper():
        start = time.time()
        function()
        end = time.time()
        cost_time = (end - start) * 1000
        print('cost time is {} ms'.format(cost_time))

    return wrapper


@deco
def func():
    print('hello')
    time.sleep(1)
    print('world')


if __name__ == '__main__':
    func()
