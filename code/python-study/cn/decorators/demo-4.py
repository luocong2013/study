# 带有不定参数的装饰器
import time


def deco(function):
    def wrapper(*args, **kwargs):
        start = time.time()
        function(*args, **kwargs)
        end = time.time()
        cost_time = (end - start) * 1000
        print('cost time is {} ms'.format(cost_time))

    return wrapper


@deco
def func(a, b):
    print('hello, here is a func for add: ')
    time.sleep(1)
    print('result is {}'.format(a + b))


@deco
def func2(a, b, c):
    print('hello, here is a func2 for add: ')
    time.sleep(1)
    print('result is {}'.format(a + b + c))


if __name__ == '__main__':
    func(3, 4)
    func2(3, 4, 5)
