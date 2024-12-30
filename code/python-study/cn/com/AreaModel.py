import math


# 定义园的面积
def area_of_circle(r):
    try:
        f = float(r)
        S = math.pi * f * f
        return S
    except ValueError:
        return 'Please enter a valid number'

r1 = 12.34
r2 = 9.08
r3 = 73.1

s1 = area_of_circle(r1)
s2 = area_of_circle(r2)
s3 = area_of_circle(r3)

print('%.2f, %.2f, %.2f' % (s1, s2, s3))
