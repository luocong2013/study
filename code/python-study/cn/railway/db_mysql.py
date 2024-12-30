import pymysql


class DbMySql(object):
    """操作MySQL数据库工具类"""

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
        self.__ddl_sql = "CREATE TABLE IF NOT EXISTS station(" \
                         "id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键'," \
                         "station_name VARCHAR(50) NULL DEFAULT NULL COMMENT '站名'," \
                         "station_sign VARCHAR(10) NULL DEFAULT NULL COMMENT '站标志'," \
                         "station_name_qp VARCHAR(100) NULL DEFAULT NULL COMMENT '站名全拼'," \
                         "station_name_jp VARCHAR(10) NULL DEFAULT NULL COMMENT '站名简拼'," \
                         "station_xh INT(11) NULL DEFAULT NULL COMMENT '站序号'," \
                         "PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8"
        self.__dcl_sql = "INSERT INTO station(station_name, station_sign, station_name_qp, station_name_jp, " \
                         "station_xh) VALUES (%s, %s, %s, %s, %s)"

        # 创建业务表
        self.create_table(self.__ddl_sql)

    def create_table(self, sql):
        """创建业务表"""
        if sql is None:
            return

        # 创建连接
        connection = pymysql.connect(**self.__config)
        try:
            with connection.cursor() as cursor:
                # 执行sql语句
                cursor.execute(sql)
                # 提交执行
                connection.commit()
                print("业务表创建成功")
        except:
            print("业务表创建失败")
            connection.rollback()
        finally:
            connection.close()

    def save_data(self, data):
        """保存单条数据"""
        if data is None:
            return
        # 创建连接
        connection = pymysql.connect(**self.__config)
        try:
            with connection.cursor() as cursor:
                # 执行sql语句
                cursor.execute(self.__dcl_sql, data)
                # 提交执行
                connection.commit()
                print("数据保存成功：{}".format(data))
        except Exception as e:
            print(repr(e))
            connection.rollback()
        finally:
            connection.close()

    def batch_save_data(self, datas):
        """批量保存数据"""
        if datas is None or len(datas) == 0:
            return
        # 创建连接
        connection = pymysql.connect(**self.__config)
        try:
            with connection.cursor() as cursor:
                # 执行sql语句
                cursor.executemany(self.__dcl_sql, datas)
                # 提交执行
                connection.commit()
        except Exception as e:
            print(repr(e))
            connection.rollback()
        finally:
            connection.close()
