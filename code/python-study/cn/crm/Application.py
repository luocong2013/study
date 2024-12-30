import requests

from cn.crm.log import logger, decorator


class Application(object):
    def __init__(self):
        self._json_headers = {'Content-Type': 'application/json;charset=UTF-8'}
        self._total_order_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/totalOrder/toU8/v1'
        self._direct_order_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/directOrder/v1/toU8'

    @decorator
    def handle_total_order(self, order_id_):
        data = {'id': order_id_}
        response = requests.post(url=self._total_order_url, data=data)
        logger.info('>>>总代订单, 处理完成<<<')
        logger.info('response.headers: %s', response.headers)
        logger.info('response.status_code: %s', response.status_code)
        # logger.info('response.json: %s', response.json())

    @decorator
    def handle_direct_order(self, order_id_):
        data = {'orderId': order_id_}
        response = requests.post(url=self._direct_order_url, data=data)
        logger.info('>>>直销订单, 处理完成<<<')
        logger.info('response.headers: %s', response.headers)
        logger.info('response.status_code: %s', response.status_code)
        # logger.info('response.json: %s', response.json())


if __name__ == '__main__':
    """CRM异常订单处理"""
    application = Application()
    application.handle_total_order(65903598239219712)
    # application.handle_direct_order()
