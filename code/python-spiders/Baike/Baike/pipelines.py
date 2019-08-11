# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html

from Baike import settings
from Baike.utils.elasticsearch_util import ElasticSearchClient


class BaikePipeline(object):
    """将百度百科数据写入ElasticSearch"""

    def __init__(self):
        self.client = ElasticSearchClient(settings.ELASTICSEARCH_HOST,
                                          settings.ELASTICSEARCH_PORT,
                                          settings.ELASTICSEARCH_INDEX_NAME)

    def open_spider(self, spider):
        """
        在Spider开启的时候被自动调用
        :param spider: Spider对象，即生成该Item的Spider
        :return:
        """
        pass

    def close_spider(self, spider):
        """
        在Spider关闭的时候自动调用
        :param spider: Spider对象，即生成该Item的Spider
        :return:
        """
        pass

    def process_item(self, item, spider):
        """
        必须要实现的方法，被定义的Item Pipeline会默认调用这个方法对Item进行处理
        :param item: Item对象，即被处理的Item
        :param spider: Spider对象，即生成该Item的Spider
        :return:
        """
        self.client.index(dict(item))
        return item
