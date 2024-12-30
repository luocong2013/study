from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import requests
"""
driver.page_source （查看网页源码）
driver.get_cookies() （获取当前浏览器的全部cookies）
driver.current_url （获取当前页面的url）
driver.close() （退出当前页面，但浏览器还在）
driver.quit() （退出浏览器）
driver.add_cookie({'name': 'xxxx', 'value': 'xxxxxxxxxx'}) （添加Cookie）
driver.refresh() （刷新页面）
driver.switch_to.frame() （切换iframe）
```例如
    g_frame = driver.find_element_by_id('g_iframe')
    driver.switch_to.frame(g_frame)
```

find_element_by_id （返回一个元素）
find_elements_by_xpath （返回一个包含元素的列表）
find_elements_by_link_text （根据链接文本获取元素列表）
find_elements_by_partial_link_text （根据链接包含的文本获取元素列表）
find_elements_by_tag_name （根据标签名获取元素列表）
find_elements_by_class_name （根据类名获取元素列表）
......
......
......

获取文本属性：
element.text （获取文本）
element.get_attribute("href") （获取属性值）
element.send_keys() （给输入框赋值）
element.click() （操作点击事件）

更换UA
options.add_argument('--user-agent=Mozilla/5.0 HAHA')

使用代理IP
driver_proxy = '--proxy-server={}'.format('http://192.168.0.110:9000')
options.add_argument(driver_proxy)
"""


def main():
    options = Options()
    # 开启无界面模式
    options.add_argument('--headless')
    # 可选项：禁用gpu，可以解决一些莫名的问题
    options.add_argument('--disable-gpu')
    driver = webdriver.Chrome(executable_path='E:/temp/chromedriver.exe', options=options)

    driver.get('https://music.163.com/#/discover/artist')

    # 定位并切换至 里面的frame
    g_frame = driver.find_element_by_id('g_iframe')
    driver.switch_to.frame(g_frame)
    # 定位元素
    eles = driver.find_elements_by_xpath('//div[@class="g-wrap"]/div[@class="m-sgerlist"]/ul/li/div/a[@class="msk"]')
    for ele in eles:
        href = ele.get_attribute('href')
        driver.get(href)
        g_frame = driver.find_element_by_id('g_iframe')
        driver.switch_to.frame(g_frame)
        mas = driver.find_elements_by_xpath('//td[2]/div/div/div/span/span')
        for ma in mas:
            mid = ma.get_attribute('data-res-id')
            if mid is not None:
                singer_url = 'http://music.163.com/song/media/outer/url?id={}.mp3'.format(mid)
                response = requests.get(singer_url)
                buff = response.content
                with open('E:/temp/music/{}.mp3'.format(mid), 'wb') as f:
                    f.write(buff)
        print(href)
    # 切换到正常页面
    current_windows = driver.window_handles
    driver.switch_to.window(current_windows[0])
    # 操作正常页面的标签
    mele = driver.find_element_by_xpath('//a')
    print(mele.text)

    driver.close()
    driver.quit()


if __name__ == '__main__':
    main()
