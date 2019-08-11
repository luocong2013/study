# -*- coding: utf-8 -*-
import scrapy
from scrapy.http import Request
from python_scrapy.items import BaikeItem


class BaikeSpider(scrapy.Spider):
    name = 'baike'
    allowed_domains = ['baike.baidu.com']
    start_urls = ['http://baike.baidu.com/']

    def parse(self, response):
        infos = response.xpath('//*/a[@href]')
        for info in infos:
            href = info.xpath('.//@href').extract()[0]
            if 'item' in href:
                link = response.urljoin(href)
                yield Request(link, callback=self.parse_item)

    def parse_item(self, response):
        """爬取每条百度百科词条"""
        # 本页面词条的链接
        infos = response.xpath('//*/a[@href]')
        for info in infos:
            href = info.xpath('.//@href').extract()[0]
            if 'item' in href:
                link = response.urljoin(href)
                yield Request(link, callback=self.parse_item)

        titles = response.xpath('//dd[@class="lemmaWgt-lemmaTitle-title"]/h1/text()').extract()
        if not titles:
            return
        contents = response.xpath('//div[@class="lemma-summary"]/div/text()').extract()
        content = ""
        for item in contents:
            content += item
        baike_item = BaikeItem()
        baike_item['baike_link'] = response.url
        baike_item['baike_title'] = titles[0]
        baike_item['baike_content'] = content
        yield baike_item
