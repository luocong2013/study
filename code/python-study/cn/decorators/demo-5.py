# 多个装饰器
import time


def deco01(function):
    def wrapper(*args, **kwargs):
        print('this is deco01')
        start = time.time()
        function(*args, **kwargs)
        end = time.time()
        cost_time = (end - start) * 1000
        print('cost time is {} ms'.format(cost_time))
        print('deco01 end here')

    return wrapper


def deco02(function):
    def wrapper(*args, **kwargs):
        print('this is deco02')
        function(*args, **kwargs)
        print('deco02 end here')

    return wrapper


@deco01
@deco02
def func(a, b):
    print('hello, here is a func for add: ')
    time.sleep(1)
    print('result is {}'.format(a + b))


if __name__ == '__main__':
    func(3, 4)
