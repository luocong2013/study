import logging

# 默认日志级别 warning：在默认情况下，只打印 warning 级别及以上的日志，低于该级别以下的日志信息将会被忽略
# 日志级别修改：可通过 logging.basiConfig 进行调整
# root：日志记录器的名称，会随文件位置的改变而改变

# format 中常用参数:
# %(name)s      : logger 的名字
# %(asctime)s   : 时间
# %(filename)s  : 执行文件名
# %(message)s   : 日志信息
# %(pathname)s  : 执行程序的路径名
# %(process)d   : 进程ID
# %(thread)d    : 线程ID
# %(threadName)s: 线程名称
# %(levelno)s   : 日志级别的数值
# %(levelname)s : 日志级别的名称
# %(lineno)d    : 调用日志的代码所在行

# logging.basicConfig：基础设置
logging.basicConfig(
    level=logging.DEBUG, # 修改日志级别
    format='%(asctime)s %(name)s [%(pathname)s line:%(lineno)d] %(levelname)s %(message)s', # 日志格式
    datefmt='%Y-%m-%d %H:%M:%S', # 日期时间格式
    # filename='log.txt', # 日志写入文件，日志文件名
    # filemode='w' # 日志模式，如：w：写、a+：追加写 等
)

logging.debug('debug')
logging.info('info')
logging.warning('warning')
logging.error('error')
logging.critical('critical')

logging.debug('%s, %s, %s', *('error', 'debug', 'info'))
logging.debug('%(module)s, %(info)s', {'module': 'log', 'info': 'error'})

logging.log(logging.INFO, '%(module)s %(info)s', {'module': 'log日志', 'info': 'info信息'}) # INFO log日志 info信息
logging.log(logging.ERROR,'再来一遍：%s, %s', *('log日志', 'error信息'))  # ERROR 再来一遍：log日志, error信息

