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

# 创建连接
connection = pymysql.connect(**config)

dcl_sql = "SELECT * FROM images"

with connection.cursor() as cursor:
    cursor.execute(dcl_sql)
    result = cursor.fetchall()
    num = 1
    for row in result:
        pic_bin = row['pic_bin']
        image = b64.b64decode(pic_bin)
        with open('D:/mywork/pycharm_workspace/FirstPython/images/aa-%d.jpg' % num, 'wb') as fp:
            fp.write(image)
            num += 1

connection.close()
