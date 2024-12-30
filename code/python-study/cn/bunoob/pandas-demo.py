import pandas as pd

now = pd.Timestamp.now()
last_day = pd.Timestamp(now.year, now.month, 1) + pd.DateOffset(months=1, days=-1)
print('当前日期: {}'.format(now))
print('当前月份的最大日期: {}'.format(last_day))
