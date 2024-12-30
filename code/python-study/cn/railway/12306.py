import ssl
import requests
from json import loads

ssl._create_default_https_context = ssl._create_unverified_context


# 分割后索引为：
# 1  =》 “预定” 字符
# 3  =》 车次
# 4  =》 出发地1
# 5  =》 目的地1
# 6  =》 出发地2
# 7  =》 目的地2
# 8  =》 出发时间
# 9  =》 到达时间
# 10 =》 总共用时
# 11 =》 是否有票（Y: 有，N: 没有）
# 13 =》 日期
# 22 =》 其他
# 23 =》 软卧
# 26 =》 无座
# 28 =》 硬卧
# 29 =》 硬座
# 30 =》 二等座
# 31 =》 一等座
# 32 =》 商务座特等座
# 33 =》 动卧
class MyRailWay(object):
    def __init__(self, train_date, from_station, to_station):
        self.__train_date = train_date
        self.__from_station = from_station
        self.__to_station = to_station

    def getList(self):
        url = 'https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date={0:s}&leftTicketDTO.from_station=' \
              '{1:s}&leftTicketDTO.to_station={2:s}&purpose_codes=ADULT'.format(self.__train_date,
                                                                                self.__from_station, self.__to_station)
        response = requests.get(url)
        response.encoding = 'utf-8'
        text = response.text
        dict = loads(text)
        return dict['data']['result']

    def data_process(self):
        results = self.getList()
        n = 0
        for str in results:
            if n == 0:
                a = 0
                tmp_list = str.split('|')
                for tmp in tmp_list:
                    print('[{0:d} {1:s}]'.format(a, tmp))
                    a += 1
            n += 1


if __name__ == '__main__':
    train_date = '2018-05-20'
    from_station = 'CDW'
    to_station = 'CQW'
    myrailway = MyRailWay(train_date, from_station, to_station)
    myrailway.data_process()
