# 带有参数的装饰器
import time


def deco(function):
    def wrapper(a, b):
        start = time.time()
        function(a, b)
        end = time.time()
        cost_time = (end - start) * 1000
        print('cost time is {} ms'.format(cost_time))

    return wrapper


@deco
def func(a, b):
    print('hello, here is a func for add: ')
    time.sleep(1.5)
    print('result is {}'.format(a + b))


if __name__ == '__main__':
    func(3, 4)
