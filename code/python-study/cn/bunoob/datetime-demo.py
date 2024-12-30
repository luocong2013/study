from datetime import datetime, date, time, timedelta

current_datetime = datetime.now()
print('当前日期和时间: {}'.format(current_datetime))
print('当前时间和日期的毫秒: {}'.format(int(current_datetime.timestamp() * 1000)))

formated_datetime = current_datetime.strftime('%Y-%m-%d %H:%M:%S')
print('格式化后的日期和时间: {}'.format(formated_datetime))

date_string = '2023-10-01 12:23:55'
parsed_datetime = datetime.strptime(date_string, '%Y-%m-%d %H:%M:%S')
print('解析后的日期和时间: {}'.format(parsed_datetime))

d1 = datetime(2023, 10, 1, 12, 22, 40)
print('特定日期和时间: {}'.format(d1))

timestamp = 1554887182000
print('时间戳转日期: {}'.format(datetime.fromtimestamp(timestamp/1000)))


def get_start_time(bill_date_):
    t = datetime.strptime(bill_date_, '%Y-%m')
    return int(t.timestamp() * 1000)


def get_end_time(bill_date_):
    t = datetime.strptime(bill_date_, '%Y-%m')
    last_day_of_month = t.replace(day=28) + timedelta(days=4)
    last_day_of_month = last_day_of_month.replace(day=1) - timedelta(days=1)
    max_time = last_day_of_month.replace(hour=23, minute=59, second=59, microsecond=999999)
    return int(max_time.timestamp() * 1000)


print('指定月份最小时间戳: {}'.format(get_start_time('2024-11')))
print('指定月份最大时间戳: {}'.format(get_end_time('2024-11')))
