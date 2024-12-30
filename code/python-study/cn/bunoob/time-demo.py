# Python 提供了一个 time 和 calendar 模块可以用于格式化日期和时间
# 时间间隔是以秒为单位的浮点小数
# 每个时间戳都以自从1970年1月1日午夜（历元）经过了多长时间来表示

import time
import calendar

# 获取当前时间戳
ticks = time.time()
print("当前时间戳为：", ticks)

# 获取当前时间
localtime = time.localtime(time.time())
print("本地时间为：", localtime)

# 获取格式化的时间
globtime = time.asctime(time.localtime(time.time()))
print("本地时间为：", globtime)

# 格式化日期
# %y 两位数的年份表示（00-99）
# %Y 四位数的年份表示（000-9999）
# %m 月份（01-12）
# %d 月内中的一天（0-31）
# %H 24小时制小时数（0-23）
# %I 12小时制小时数（01-12）
# %M 分钟数（00=59）
# %S 秒（00-59）
# %a 本地简化星期名称
# %A 本地完整星期名称
# %b 本地简化的月份名称
# %B 本地完整的月份名称
# %c 本地相应的日期表示和时间表示
# %j 年内的一天（001-366）
# %p 本地A.M.或P.M.的等价符
# %U 一年中的星期数（00-53）星期天为星期的开始
# %w 星期（0-6），星期天为星期的开始
# %W 一年中的星期数（00-53）星期一为星期的开始
# %x 本地相应的日期表示
# %X 本地相应的时间表示
# %Z 当前时区的名称
# %% %号本身

# 格式化成2018-01-23 20:00:29形式
time1 = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
print("time1: ", time1)
# 格式化成Tue Jan 23 20:00:29 2018形式
time2 = time.strftime("%a %b %d %H:%M:%S %Y", time.localtime())
print("time2: ", time2)

# 将格式字符串转换为时间戳
a = "2018-01-23 20:00:29"
time3 = time.mktime(time.strptime(a, "%Y-%m-%d %H:%M:%S"))
print("time3: ", time3)

# 返回格林威治西部的夏令时地区的偏移秒数。如果该地区在格林威治东部会返回负值（如西欧，包括英国）。对夏令时启用地区才能使用
print("time.altzone: {0}".format(time.altzone))
# 接受时间元组并返回一个可读的形式
print("time.asctime(t): {0}".format(time.asctime(time.localtime())))
# 用以浮点数计算的秒数返回当前的CPU时间。用来衡量不同程序的耗时
print("time.clock(): {0}".format(time.clock()))
# 作用相当于asctime(localtime(secs))，未给参数相当于asctime()
print("time.ctime(): {0}".format(time.ctime()))
# 接收时间辍（1970纪元后经过的浮点秒数）并返回格林威治天文时间下的时间元组
print("time.gmtime(): {0}".format(time.gmtime(1516710000.7210739)))
# 接收时间辍（1970纪元后经过的浮点秒数）并返回当地时间下的时间元组
print("time.localtime(): {0}".format(time.localtime()))
# 推迟调用线程的运行，secs指秒数
time.sleep(2)

# 获取某月日历
cal = calendar.month(2018, 1)
print("以下输出2018年1月份的日历:")
print(cal)
