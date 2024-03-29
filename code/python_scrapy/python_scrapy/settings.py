# -*- coding: utf-8 -*-

# Scrapy settings for python_scrapy project
#
# For simplicity, this file contains only settings considered important or
# commonly used. You can find more settings consulting the documentation:
#
#     https://doc.scrapy.org/en/latest/topics/settings.html
#     https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
#     https://doc.scrapy.org/en/latest/topics/spider-middleware.html

BOT_NAME = 'python_scrapy'

SPIDER_MODULES = ['python_scrapy.spiders']
NEWSPIDER_MODULE = 'python_scrapy.spiders'

# Crawl responsibly by identifying yourself (and your website) on the user-agent
# USER_AGENT = 'python_scrapy (+http://www.yourdomain.com)'

# Obey robots.txt rules
ROBOTSTXT_OBEY = False

# Configure maximum concurrent requests performed by Scrapy (default: 16)
# 并发数
CONCURRENT_REQUESTS = 1

# Configure a delay for requests for the same website (default: 0)
# See https://doc.scrapy.org/en/latest/topics/settings.html#download-delay
# See also autothrottle settings and docs
# DOWNLOAD_DELAY = 3
# The download delay setting will honor only one of:
# CONCURRENT_REQUESTS_PER_DOMAIN = 16
# CONCURRENT_REQUESTS_PER_IP = 16

# Disable cookies (enabled by default)
# COOKIES_ENABLED = False

# Disable Telnet Console (enabled by default)
# TELNETCONSOLE_ENABLED = False

# Override the default request headers:
# DEFAULT_REQUEST_HEADERS = {
#   'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
#   'Accept-Language': 'en',
# }

# Enable or disable spider middlewares
# See https://doc.scrapy.org/en/latest/topics/spider-middleware.html
# SPIDER_MIDDLEWARES = {
#    'python_scrapy.middlewares.PythonScrapySpiderMiddleware': 543,
# }

# Enable or disable downloader middlewares
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
DOWNLOADER_MIDDLEWARES = {
    # 'python_scrapy.middlewares.PythonScrapyDownloaderMiddleware': 543,
    # 'python_scrapy.middlewares.RoteUserAgentMiddleware': 544,
}

# Enable or disable extensions
# See https://doc.scrapy.org/en/latest/topics/extensions.html
# EXTENSIONS = {
#    'scrapy.extensions.telnet.TelnetConsole': None,
# }

# Configure item pipelines
# See https://doc.scrapy.org/en/latest/topics/item-pipeline.html
ITEM_PIPELINES = {
    # 'python_scrapy.pipelines.PythonScrapyPipeline': 300,
    'python_scrapy.pipelines.LocalFileTiebaPipeline': 301,
    # 'python_scrapy.pipelines.MongoDBTiebaPipeline': 302,
    # 'python_scrapy.pipelines.MinioTiebaPipeline': 303,
    # 'python_scrapy.pipelines.ElasticSearchBaikePipeline': 304,
}

# Enable and configure the AutoThrottle extension (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/autothrottle.html
# AUTOTHROTTLE_ENABLED = True
# The initial download delay
# AUTOTHROTTLE_START_DELAY = 5
# The maximum download delay to be set in case of high latencies
# AUTOTHROTTLE_MAX_DELAY = 60
# The average number of requests Scrapy should be sending in parallel to
# each remote server
# AUTOTHROTTLE_TARGET_CONCURRENCY = 1.0
# Enable showing throttling stats for every response received:
# AUTOTHROTTLE_DEBUG = False

# Enable and configure HTTP caching (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html#httpcache-middleware-settings
# HTTPCACHE_ENABLED = True
# HTTPCACHE_EXPIRATION_SECS = 0
# HTTPCACHE_DIR = 'httpcache'
# HTTPCACHE_IGNORE_HTTP_CODES = []
# HTTPCACHE_STORAGE = 'scrapy.extensions.httpcache.FilesystemCacheStorage'


# 自定义变量
# 本地图片存储目录
IMAGES_STORE = '/Volumes/硬盘/工作资料/.pic'

# MongoDB
# ①使用这种URI连接MongoDB不需要后面的认证
MONGODB_URI = 'mongodb://tieba:tieba@192.168.0.105:27017/tieba'

# ②使用这种URI连接MongoDB需要后面的认证，就需要用户名、密码
# MONGODB_URI = 'mongodb://192.168.0.105:27017'
# MONGODB_USER = 'tieba'
# MONGODB_PSSWD = 'tieba'

MONGODB_DB_NAME = 'tieba'
MONGODB_COLL_NAME = 'image'

# Minio
MINIO_ENDPOINT = '192.168.0.111:9000'
MINIO_ACCESS_KEY = 'dev'
MINIO_SECRET_KEY = '123456789'
MINIO_BUCKET = 'tieba'
MINIO_TEMP_DIRECTORY = "D:/pic/.minio"

# ElasticSearch
ELASTICSEARCH_HOST = '192.168.0.111'
ELASTICSEARCH_PORT = 9200
ELASTICSEARCH_INDEX_NAME = 'baike'
