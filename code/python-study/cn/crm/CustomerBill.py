import requests

from datetime import datetime, timedelta
from requests import HTTPError
from cn.crm.log import logger, decorator


def get_start_time(bill_date_):
    t = datetime.strptime(bill_date_, '%Y-%m')
    return int(t.timestamp() * 1000)


def get_end_time(bill_date_):
    t = datetime.strptime(bill_date_, '%Y-%m')
    last_day_of_month = t.replace(day=28) + timedelta(days=4)
    last_day_of_month = last_day_of_month.replace(day=1) - timedelta(days=1)
    max_time = last_day_of_month.replace(hour=23, minute=59, second=59, microsecond=999999)
    return int(max_time.timestamp() * 1000)


class CustomerBill(object):
    def __init__(self):
        self._file_name_tuple = (
            'live.txt', 'record_charge.txt', 'sdk.txt', 'sdk_detail.txt', 'wechat.txt', 'wechat_detail.txt',
            'special_participant_export.txt', 'ent_cascade_mcu_event.txt')
        self._json_headers = {'Content-Type': 'application/json;charset=UTF-8'}
        self._read_oss_file_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/customer/bill/oss/v1'
        self._update_done_time_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/customer/bill/doneTime/v1'
        self._update_price_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/customer/bill/price/v1'
        self._data_process_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/customer/bill/process/v1'
        self._check_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/customer/bill/check/v1'

    @decorator
    def read_oss_file(self, bill_date_):
        """
        客户后付费账单-拉取oss文件（第一步）
        :param bill_date_:
        :return:
        """
        for file_name in self._file_name_tuple:
            object_name = 'sdk_bill_export/prd/{}/{}'.format(bill_date_, file_name)
            params = {'objectName': object_name, 'encrypt': False, 'jsonStr': True}
            logger.info('>>>object_name: %s 下载开始, params: %s<<<', *(object_name, params))
            try:
                response = requests.get(url=self._read_oss_file_url, params=params)
                logger.info('>>>object_name: %s 下载成功<<<', object_name)
                logger.info('response.headers: %s', response.headers)
                logger.info('response.status_code: %d', response.status_code)
            except HTTPError as e:
                logger.error('>>>object_name: %s 下载失败<<<, 请求异常: %s', *(object_name, e))
            finally:
                logger.info('>>>object_name: %s 下载结束<<<', object_name)

    @decorator
    def update_done_time(self, bill_date_):
        """
        客户后付费账单-补全事件表上的done_time字段（第二步）
        :param bill_date_:
        :return:
        """
        data = {'startDate': get_start_time(bill_date_), 'endDate': get_end_time(bill_date_)}
        logger.info('>>>处理开始, data: %s<<<', data)
        response = requests.post(url=self._update_done_time_url, data=data)
        logger.info('>>>处理完成<<<')
        logger.info('response.headers: %s', response.headers)
        logger.info('response.status_code: %d', response.status_code)

    @decorator
    def update_price(self, bill_date_):
        """
        客户后付费账单-填充单价字段（第三步）
        :param bill_date_:
        :return:
        """
        data = {'billDateStart': get_start_time(bill_date_), 'billDateEnd': get_end_time(bill_date_)}
        logger.info('>>>处理开始, data: %s<<<', data)
        response = requests.post(url=self._update_price_url, data=data)
        logger.info('>>>处理完成<<<')
        logger.info('response.headers: %s', response.headers)
        logger.info('response.status_code: %d', response.status_code)

    @decorator
    def data_process(self, bill_date_):
        """
        客户后付费账单-手动处理数据（第四步）
        :param bill_date_:
        :return:
        """
        params = {'billDateStart': get_start_time(bill_date_), 'billDateEnd': get_end_time(bill_date_),
                  'processTime': 0}
        logger.info('>>>处理开始, params: %s<<<', params)
        response = requests.get(url=self._data_process_url, params=params)
        logger.info('>>>处理完成<<<')
        logger.info('response.headers: %s', response.headers)
        logger.info('response.status_code: %d', response.status_code)

    @decorator
    def check(self, bill_date_):
        """
        客户后付费账单-检查（第五步）
        :param bill_date_:
        :return:
        """
        params = {'startDate': get_start_time(bill_date_), 'endDate': get_end_time(bill_date_)}
        logger.info('>>>处理开始, params: %s<<<', params)
        response = requests.get(url=self._check_url, params=params)
        logger.info('>>>处理完成<<<')
        logger.info('response.headers: %s', response.headers)
        logger.info('response.status_code: %d', response.status_code)


if __name__ == '__main__':
    """CRM客户后付费账单数据处理"""
    bill_date = '2024-07'
    app = CustomerBill()

    # 7月还差一个文件
    app.read_oss_file(bill_date)
    # app.update_done_time(bill_date)
    # app.update_price(bill_date)
    # app.data_process(bill_date)
    # app.check(bill_date)
