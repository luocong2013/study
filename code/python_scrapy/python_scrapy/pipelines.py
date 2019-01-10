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
import hashlib

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
        self.db_client = MongoClient(settings.MONGODB_URI)
        self.db = self.db_client.get_database(settings.MONGODB_DB_NAME)
        # 认证
        # self.db.authenticate('tieba', 'tieba')
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
    # 存在返回 True, 不存在返回 False
    def query_db(self, item):
        image_link_md5 = hashlib.md5(item['image_link'].encode(encoding='UTF-8')).hexdigest()
        db_query = {"image_link_md5": image_link_md5}
        db_data = self.db_collection.find(db_query, {"_id": 0, "imsge_link_md5": 1})
        return db_data

    # 对数据进行处理
    def process_item(self, item, spider):
        if isinstance(item, ImageItem):
            if not self.query_db(item):
                self.insert_db(item)
            else:
                print('图片已经存在了！！！')
        return item
