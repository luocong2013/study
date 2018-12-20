# -*- coding: utf-8 -*-
import scrapy
from scrapy.http import Request
from python_scrapy.items import ImageItem


class YoonjooSpider(scrapy.Spider):
    name = 'yoonjoo'
    allowed_domains = ['tieba.baidu.com']
    start_urls = ['http://tieba.baidu.com/f?kw=%E5%AD%99%E5%85%81%E7%8F%A0']

    def parse(self, response):
        pager_bottons = response.xpath('//*[@id="frs_list_pager"]/a')
        for pager_botton in pager_bottons:
            pager_botton_text = pager_botton.xpath('.//text()').extract()
            if '下一页>' in pager_botton_text:
                next_href = pager_botton.xpath('.//@href').extract()[0]
                next_link = response.urljoin(next_href)
                yield Request(next_link, callback=self.parse)

        infos = response.xpath('//*[@class=" j_thread_list clearfix"]/div/div[2]/div[1]/div[1]/a[@class="j_th_tit "]')
        for info in infos:
            href = info.xpath('.//@href').extract()[0]
            link = response.urljoin(href)
            yield Request(link, callback=self.parse_item)

    def parse_item(self, response):
        """抓取每个链接里的图片"""
        image_title = response.xpath('//*[@id="j_core_title_wrap"]/div[2]/h1/text()').extract()[0]
        imgs = response.xpath('//*[@class="d_post_content j_d_post_content  clearfix"]/img[@class="BDE_Image"]')
        for img in imgs:
            image_item = ImageItem()
            image_link = img.xpath('.//@src').extract()[0]
            image_name = image_link.split('/')[-1]
            image_item['image_title'] = image_title
            image_item['image_link'] = image_link
            image_item['image_name'] = image_name
            # 返回爬取到的信息
            yield image_item
