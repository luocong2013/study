# -*- coding: utf-8 -*-
import scrapy


class CloudmusicSpider(scrapy.Spider):
    name = 'cloudmusic'
    allowed_domains = ['music.163.com']
    start_urls = ['https://music.163.com/#/discover/playlist/']

    def parse(self, response):
        print(response.text)
        pass
