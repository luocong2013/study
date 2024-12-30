from urllib import request
from bs4 import BeautifulSoup
import pymysql
import base64 as b64

config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': 'root',
    'db': 'spider',
    'charset': 'utf8',
    'cursorclass': pymysql.cursors.DictCursor
}
dcl_sql = "INSERT INTO images(url, title, pic_bin) VALUES (%s, %s, %s)"
# 创建连接
connection = pymysql.connect(**config)

url = "http://tieba.baidu.com/p/5513894858"
response1 = request.urlopen(url)
buff = response1.read()
soup = BeautifulSoup(buff, 'html.parser', from_encoding='utf-8')
links = soup.find_all('img', class_='BDE_Image')

for link in links:
    try:
        src = link['src']
        resp = request.urlopen(src)
        b = resp.read()
        pic_bin = str(b64.b64encode(b))[2:-1]
        print(src)
        with connection.cursor() as cursor:
            # 执行sql语句
            param = (src, '111', pic_bin)
            cursor.execute(dcl_sql, param)
            # 提交执行
            connection.commit()
    except Exception as e:
        print("数据表创建异常: " + repr(e))
        connection.rollback()

connection.close()


# html = buff.decode('UTF-8')
# print(html)
