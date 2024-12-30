import requests
from cn.railway import db_mysql


class GetCity(object):
    def __init__(self):
        self.__url = 'https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9053'

    def __getCityText(self):
        response = requests.get(self.__url)
        text = response.text
        return text

    def data_process(self):
        cityText = self.__getCityText()
        begin = cityText.find("'") + 1
        cityText = cityText[begin + 1: cityText.find("'", begin)]
        city_list = cityText.split('@')
        datas = []
        for city in city_list:
            temp_item_list = city.split('|')
            data = (temp_item_list[1], temp_item_list[2], temp_item_list[3], temp_item_list[4], int(temp_item_list[5]))
            datas.append(data)
        # 数据持久化
        mysql = db_mysql.DbMySql()
        mysql.batch_save_data(datas)


if __name__ == '__main__':
    getCity = GetCity()
    getCity.data_process()
