import requests
from urllib import parse
from bs4 import BeautifulSoup

url = "http://tieba.baidu.com/f?kw=%E5%AD%99%E5%85%81%E7%8F%A0"

print(parse.unquote(url))

response = requests.get(url)
soup = BeautifulSoup(response.content, 'html.parser', from_encoding='utf-8')
links = soup.find_all('a', class_='j_th_tit')

for link in links:
    href = link['href']
    newurl = parse.urljoin(url, href)
    title = link['title']
    print(newurl)
    print(title)


# html = buff.decode('UTF-8')
# print(html)
