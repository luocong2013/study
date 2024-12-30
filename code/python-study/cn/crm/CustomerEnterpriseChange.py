import requests

from datetime import datetime
from cn.crm.log import logger, decorator


class CustomerEnterpriseChange(object):
    def __init__(self):
        self._json_headers = {'Content-Type': 'application/json;charset=UTF-8'}
        self._call_api_url = 'http://127.0.0.1:8086/api/rest/xy-crm/internal/callApi/v1'
        self._query_enterprise_info_url = 'https://openapi-xycrm.xylink.com/console/api/rest/internal/v1/en/enterprise/info?enterpriseid={}'

    @decorator
    def query_enterprise_info(self, enterprise_id_, customer_code_):
        request_url = self._query_enterprise_info_url.format(enterprise_id_)
        json_params = {
            'requestUrl': request_url,
            'requestMethod': 'GET',
            'requestBody': ''
        }
        response = requests.post(url=self._call_api_url, json=json_params, headers=self._json_headers)
        json = response.json()
        admin_mail_box = json['adminMailbox'] if json['adminMailbox'] is not None else ''
        logger.info(
            'update xycrm.customer_info set enterprise_id = \'%s\', enterprise_create_time = \'%s\', enterprise_name = \'%s\', '
            'enterprise_admin_id = \'%d\', enterprise_admin_name = \'%s\', enterprise_admin_phone = \'%s\', enterprise_admin_email = \'%s\' '
            'where customer_code = \'%s\';',
            *(json['id'], datetime.fromtimestamp(json['createTime'] / 1000), json['enterpriseName'],
              json['adminId'], json['adminName'], json['adminPhone'], admin_mail_box, customer_code_))
        # logger.info('response.headers: %s', response.headers)


if __name__ == '__main__':
    """更新客户企业ID"""
    app = CustomerEnterpriseChange()

    enterprise_id_list = [
        ('000000006ff9ba25016ffa71eb1401b8', '2017074562'),
        ('ff8080817213ec0d0172310431c60796', '2020031748'),
        ('2c94bb916fb4612a016feb089cd712e4', '2020050163'),
        ('96809fd683f0d56901852d70253a3852', '202212060045'),
        ('ff80808164dc88d2016502c9bb7604c4', '2018074027'),
        ('ff8080816b5ba5b3016b887876cb1d43', '2021081378'),
        ('ff80808172d246f10172ff3a455c0ed3', '202203220076'),
        ('9680dbc877dedd9b0177fbf1c1fd0741', '202205180059'),
        ('ff8080815f87d02d015fc291e0a70b86', '2021080191'),
        ('ff80808166e46b6c016787d78a892d9c', '2019120301'),
        ('2c94bb91723d20590172596092ed099d', '2017075863'),
        ('9680fca574118c8b01741f3de19f018f', '2020080569'),
        ('ff80808169a5ef61016a067f88041513', '2018051559')
    ]

    for item in enterprise_id_list:
        app.query_enterprise_info(item[0], item[1])
