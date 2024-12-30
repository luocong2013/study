import logging

# 进阶示例：
# ① logging 只能单纯的将日志输出到终端 或 文件中
# ② 若想将日志同时输出到终端 和 文件中，则需要如下处理

# 1.实例化 logger 对象
logger = logging.getLogger('admin')
# logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)  # 设置日志级别

# 2.定义 Handler 对象，常用的有 StreamHandler 和 FileHandler
console_handler = logging.StreamHandler()  # 终端
file_handler = logging.FileHandler(filename='log.txt')  # 文件

# 3.定义 Formatter 格式
formatter = logging.Formatter('%(asctime)s %(name)s [%(pathname)s line:%(lineno)d] %(levelname)s %(message)s')

# 4.定义 Filter 过滤器：判断给定字符 与 logger 的名称前缀是否匹配
# 可选操作，默认全匹配
# flt = logging.Filter('admin')  # 若匹配则打印

# 5.将 Handler 与 Formatter 绑定、Logger 与 Handler、Filter 绑定
console_handler.setFormatter(formatter)
file_handler.setFormatter(formatter)

logger.addHandler(console_handler)
logger.addHandler(file_handler)

# logger.addFilter(flt)

# 6.打印测试
logger.debug('debug')
logger.info('info信息')
logger.warning('warning')
logger.error('error')
logger.critical('critical')
