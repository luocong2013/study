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
