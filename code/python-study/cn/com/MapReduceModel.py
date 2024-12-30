from functools import reduce


def f(x):
    return x * x


numLst = [1, 2, 3, 4, 5, 6, 7, 8, 9]
r = map(f, numLst)

print(list(r))
print(list(map(str, numLst)))


def add(x, y):
    return x + y


print(reduce(add, numLst))

DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}


def str2int(s):
    def fn(x, y):
        return x * 10 + y

    def char2num(s):
        return DIGITS[s]

    return reduce(fn, map(char2num, s))


print(str2int('1357910999'))
st = 'LUOC'
print(st.capitalize())
