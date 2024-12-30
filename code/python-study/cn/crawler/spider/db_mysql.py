import pymysql


class Db_MySQL(object):
    def __init__(self):
        self.__config = {
            'host': '127.0.0.1',
            'port': 3306,
            'user': 'root',
            'password': 'root',
            'db': 'spider',
            'charset': 'utf8',
            'cursorclass': pymysql.cursors.DictCursor
        }
        self.__ddl_sql = "CREATE TABLE IF NOT EXISTS images (" \
                         "id int NOT NULL AUTO_INCREMENT," \
                         "url VARCHAR(200)," \
                         "title VARCHAR(200)," \
                         "pic_bin LONGBLOB," \
                         "PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8"
        self.__dcl_sql = "INSERT INTO images(url, title, pic_bin) VALUES (%s, %s, %s)"
        # 创建连接
        connection = pymysql.connect(**self.__config)
        try:
            with connection.cursor() as cursor:
                # 执行sql语句
                cursor.execute(self.__ddl_sql)
                # 提交执行
                connection.commit()
        except:
            print("数据表创建异常")
            connection.rollback()
        finally:
            connection.close()

    def save_data(self, data):
        if data is None:
            return

        # 创建连接
        connection = pymysql.connect(**self.__config)
        try:
            with connection.cursor() as cursor:
                param = (data['url'], data['title'], data['pic_bin'])
                # 执行sql语句
                cursor.execute(self.__dcl_sql, param)
                # 提交执行
                connection.commit()
        except Exception as e:
            print(e)
            connection.rollback()
        finally:
            connection.close()

    def save_datas(self, datas):
        if datas is None or len(datas) == 0:
            return
        for data in datas:
            self.save_data(data)
