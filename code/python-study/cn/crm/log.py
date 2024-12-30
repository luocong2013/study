import logging
from datetime import datetime
from logging.config import fileConfig

fileConfig('logging.conf')

logger = logging.getLogger(__name__)


def decorator(function):
    def wrapper(*args, **kwargs):
        logger.info('start call')
        start = datetime.now().timestamp()
        function(*args, **kwargs)
        end = datetime.now().timestamp()
        cost_time = int((end - start) * 1000)
        logger.info('end call, cost time %d ms', cost_time)

    return wrapper
