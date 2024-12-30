# 日志配置字典
log_config = {
    'version': 1,  # 必填，其他选填
    'handlers': {
        'console': {  # 定义一个名为 "console" 的处理器
            'class': 'logging.StreamHandler',
            'level': 'INFO',
            'formatter': 'simple',
            'stream': 'ext://sys.stdout'  # 使用标准输出
        },
        'file': {  # 定义一个名为 "file" 的处理器
            'class': 'logging.FileHandler',
            'level': 'INFO',
            'formatter': 'standard',
            'filename': 'log.txt',  # 日志文件
            'mode': 'a',
            'encoding': 'utf-8'
        }
    },
    'formatters': {
        'simple': {  # 定义一个简单的格式器
            'format': '%(asctime)s - %(name)s - [%(levelname)s] - %(message)s',
            'datefmt': '%Y-%m-%d %H:%M:%S'
        },
        'standard': {
            'format': '%(asctime)s %(name)s [%(pathname)s line:%(lineno)d] %(levelname)s %(message)s',
            'datefmt': '%Y-%m-%d %H:%M:%S'
        }
    },
    'root': {  # 配置根日志记录器
        'level': 'INFO',
        'handlers': ['console', 'file']  # 终端、文件
    }
}
