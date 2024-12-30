import logging
from logging.config import dictConfig
from cn.log import DictLogConfig

# 配置日志
dictConfig(DictLogConfig.log_config)

# 获取日志记录器
logger = logging.getLogger('admin')

# 记录日志
logger.info("info msg")
logger.warning("warning msg")

logging.log(logging.INFO, '%(module)s %(info)s', {'module': 'log日志', 'info': 'info信息'}) # INFO log日志 info信息
logging.log(logging.ERROR,'再来一遍：%s, %s', *('log日志', 'error信息'))  # ERROR 再来一遍：log日志, error信息