# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import os
import requests
from python_scrapy import settings
from python_scrapy.items import ImageItem
from pymongo import MongoClient
from minio import Minio
import hashlib
from python_scrapy.utils.elasticsearch_util import ElasticSearchClient


class PythonScrapyPipeline(object):
    def process_item(self, item, spider):
        return item


class LocalFileTiebaPipeline(object):
    """下载图片到本地 IMAGES_STORE 目录下"""

    def process_item(self, item, spider):
        try:
            dic_path = '{}/{}'.format(settings.IMAGES_STORE, item['image_title'])
            if not os.path.exists(dic_path):
                os.makedirs(dic_path)
            file_path = '{}/{}'.format(dic_path, item['image_name'])
            if not os.path.exists(file_path):
                buff = requests.get(item['image_link']).content
                with open(file_path, 'wb') as f:
                    f.write(buff)
            else:
                print('图片已经存在了！！！')
        except Exception as e:
            print(repr(e))
        return item


class MongoDBTiebaPipeline(object):
    """将数据写入到MongoDB数据库"""

    # 打开数据库
    def open_spider(self, spider):
        self.db_client = MongoClient(settings.MONGODB_URI, connectTimeoutMS=60000)
        self.db = self.db_client.get_database(settings.MONGODB_DB_NAME)
        # 认证
        # self.db.authenticate(settings.MONGODB_USER, settings.MONGODB_PSSWD)
        self.db_collection = self.db.get_collection(settings.MONGODB_COLL_NAME)

    # 关闭数据库
    def close_spider(self, spider):
        self.db_client.close()

    # 插入数据
    def insert_db(self, item):
        dic_item = dict(item)
        dic_item['image_bin'] = requests.get(item['image_link']).content
        # 对图片链接做MD5存储
        dic_item['image_link_md5'] = hashlib.md5(item['image_link'].encode(encoding='UTF-8')).hexdigest()
        self.db_collection.insert_one(dic_item)
        print('插入数据到MongoDB成功')

    # 对图片链接做MD5查询是否已存在该图片
    # 存在返回非0, 不存在返回0
    def query_db(self, item):
        image_link_md5 = hashlib.md5(item['image_link'].encode(encoding='UTF-8')).hexdigest()
        db_query = {"image_link_md5": image_link_md5}
        db_data = self.db_collection.find(db_query, {"_id": 0, "imsge_link_md5": 1})
        return db_data.count()

    # 对数据进行处理
    def process_item(self, item, spider):
        if isinstance(item, ImageItem):
            # if self.query_db(item) == 0:
            #     self.insert_db(item)
            # else:
            #     print('图片已经存在了！！！')
            self.insert_db(item)
        return item


class MinioTiebaPipeline(object):
    """将数据写入Minio"""

    def __init__(self):
        self.minio_client = Minio(endpoint=settings.MINIO_ENDPOINT,
                                  access_key=settings.MINIO_ACCESS_KEY,
                                  secret_key=settings.MINIO_SECRET_KEY,
                                  secure=False)

    # 打开爬虫
    def open_spider(self, spider):
        if not self.minio_client.bucket_exists(settings.MINIO_BUCKET):
            self.minio_client.make_bucket(settings.MINIO_BUCKET)

    def put_object(self, item, file_path):
        try:
            object_name = '{}/{}'.format(item['image_title'], item['image_name'])
            self.minio_client.fput_object(settings.MINIO_BUCKET, object_name, file_path)
        except Exception as e:
            print(repr(e))
        finally:
            os.remove(file_path)

    # 对数据进行处理
    def process_item(self, item, spider):
        try:
            if not os.path.exists(settings.MINIO_TEMP_DIRECTORY):
                os.makedirs(settings.MINIO_TEMP_DIRECTORY)
            file_path = '{}/{}'.format(settings.MINIO_TEMP_DIRECTORY, item['image_name'])
            if not os.path.exists(file_path):
                buff = requests.get(item['image_link']).content
                with open(file_path, 'wb') as f:
                    f.write(buff)
                self.put_object(item, file_path)
            else:
                print('图片已经存在了！！！')
        except Exception as e:
            print(repr(e))
        return item


class ElasticSearchBaikePipeline(object):
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
