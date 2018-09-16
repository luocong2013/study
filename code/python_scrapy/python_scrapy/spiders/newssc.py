# -*- coding: utf-8 -*-
import scrapy
from scrapy.http import Request
from python_scrapy.items import NewsscItem


class NewsscSpider(scrapy.Spider):
    name = 'newssc'
    allowed_domains = ['newssc.org']
    start_urls = ['http://www.newssc.org/']

    def parse(self, response):
        hrefs = response.xpath('//a[@class="margin-right-12"]/@href')
        for href in hrefs:
            link = href.extract()
            yield Request(link, callback=self.parse_item)

    def parse_item(self, response):
        title = response.xpath('//h1/text()').extract()[0]
        contents = response.xpath('//div[@class="content"]/p/text()').extract()
        content = ""
        for item in contents:
            content += item

        newssc_item = NewsscItem()
        newssc_item['news_link'] = response.url
        newssc_item['news_title'] = title
        newssc_item['news_content'] = content
        yield newssc_item
