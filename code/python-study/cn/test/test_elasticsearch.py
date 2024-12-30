from elasticsearch import Elasticsearch
from datetime import datetime


class ElasticObj:

    def __init__(self, host, port, index_name, timeout=3600):
        """
        :param host: elasticsearch地址
        :param port: elasticsearch端口
        :param index_name: 索引名称
        :param timeout: 超时时间，默认是10秒，如果数据量很大，时间要设置更长一些
        """
        self.host = host
        self.port = port
        self.index_name = index_name
        self.timeout = timeout
        # 无用户名密码状态
        self.es = Elasticsearch([{'host': self.host, 'port': self.port}], timeout=self.timeout)
        # 用户名密码状态
        # self.es = Elasticsearch([{'host': self.host, 'port': self.port}],
        #                         http_auth=('elastic', '123456'), timeout=self.timeout)

    def index(self, doc):
        res = self.es.index(index=self.index_name, body=doc)
        print(res)

    def get(self, docId):
        res = self.es.get(index=self.index_name, id=docId)
        print(res['_source'])

    def refresh(self):
        self.es.indices.refresh(index=self.index_name)


if __name__ == '__main__':
    obj = ElasticObj('192.168.0.111', 9200, 'baike')
    # doc = {
    #     'author': 'luocong',
    #     'text': 'Elasticsearch: cool. bonsai cool.',
    #     'timestamp': datetime.now()
    # }
    # obj.index(doc)
    obj.get('arHtfmwBZQUejUTbt9eO')
