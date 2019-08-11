# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class PythonScrapyItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass


class NewsscItem(scrapy.Item):
    """新闻数据"""
    # 新闻链接
    news_link = scrapy.Field()
    # 新闻标题
    news_title = scrapy.Field()
    # 新闻内容
    news_content = scrapy.Field()


class ImageItem(scrapy.Item):
    """图片数据"""
    # 图片标题
    image_title = scrapy.Field()
    # 图片创建日期
    image_date = scrapy.Field()
    # 图片链接地址
    image_link = scrapy.Field()
    # 图片名称
    image_name = scrapy.Field()


class BaikeItem(scrapy.Item):
    """百度百科词条"""
    # 词条链接
    baike_link = scrapy.Field()
    # 词条标题
    baike_title = scrapy.Field()
    # 词条内容
    baike_content = scrapy.Field()
