import schedule
import time
from datetime import datetime


# 自动化任务调度
def job():
    print(f"[{datetime.now()}]: 这是定时执行的任务！")


# 每2秒执行一次
schedule.every(2).seconds.do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
