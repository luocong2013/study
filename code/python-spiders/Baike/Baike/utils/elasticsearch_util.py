from elasticsearch import Elasticsearch

"""
Python操作ElasticSearch工具类
"""


class ElasticSearchClient:

    def __init__(self, host, port, index_name, timeout=3600):
        """
        构造方法
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
        self.client = Elasticsearch([{'host': self.host, 'port': self.port}], timeout=self.timeout)
        # 用户名密码状态
        # self.client = Elasticsearch([{'host': self.host, 'port': self.port}],
        #                         http_auth=('elastic', '123456'), timeout=self.timeout)

    def index(self, document):
        """
        索引文档
        :param document: 文档
        :return:
        """
        self.client.index(index=self.index_name, body=document)

    def get(self, document_id):
        """
        获取索引
        :param document_id: 文档ID
        :return:
        """
        result = self.client.get(index=self.index_name, id=document_id)
        return result['_source']

    def search(self, search_body):
        """
        获取索引
        :param search_body: 查询体
        :return:
        """
        result = self.client.search(index=self.index_name, body=search_body)
        return result
