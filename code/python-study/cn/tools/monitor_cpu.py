import psutil
import time
import threading
from datetime import datetime


# 监控CPU使用率
def monitor_cpu_usage(interval=10):
    while True:
        cpu_usage = psutil.cpu_percent(interval=1)
        print(f"[{datetime.now()}]: 当前 CPU 使用率: {cpu_usage}%")
        time.sleep(interval)


# 监控内存使用率
def monitor_memory_usage(interval=10):
    while True:
        memory_info = psutil.virtual_memory()
        print(f"[{datetime.now()}]: 内存总量：{memory_info.total / (1024 ** 3):.2f} GB")
        print(f"[{datetime.now()}]: 已使用内存：{memory_info.used / (1024 ** 3):.2f} GB")
        time.sleep(interval)


print(f"thread {threading.currentThread().name} is running...")

monitor_cpu_thread = threading.Thread(target=monitor_cpu_usage, args=(2,), name="monitor_cpu_thread")
monitor_cpu_thread.start()

monitor_memory_thread = threading.Thread(target=monitor_memory_usage, kwargs={'interval': 3},
                                         name="monitor_memory_thread")
monitor_memory_thread.start()
