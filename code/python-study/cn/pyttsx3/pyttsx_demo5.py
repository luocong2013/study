import pyttsx3
import time
from datetime import datetime


def voice_alarm(alarm_time_, message="时间到了！"):
    engine = pyttsx3.init()

    while True:
        current_time = datetime.now().strftime("%H:%M")
        if current_time == alarm_time_:
            print("闹钟响了!")
            engine.say(message)
            engine.runAndWait()
            break
        time.sleep(30)  # 每30秒检查一次


# 设置闹钟时间（24小时制）
alarm_time = "18:50"
voice_alarm(alarm_time, "该起床了，太阳晒屁股啦！")
