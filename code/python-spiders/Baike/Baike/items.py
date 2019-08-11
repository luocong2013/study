# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class BaikeItem(scrapy.Item):
    """百度百科词条"""
    # 词条链接
    baike_link = scrapy.Field()
    # 词条标题
    baike_title = scrapy.Field()
    # 词条内容
    baike_content = scrapy.Field()
