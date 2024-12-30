# MySQL数据库连接
# 学习记录：https://www.cnblogs.com/wt11/p/6141225.html
import pymysql

# 打开数据库连接
db = pymysql.connect(host='127.0.0.1',
                     port=3306,
                     user='root',
                     password='root',
                     db='dhb',
                     charset='utf8')

# 使用cursor()方法创建一个游标对象 cursor
cursor = db.cursor()

# 使用execute()方法执行SQL查询
cursor.execute("SELECT VERSION()")

# 使用fetchone()方法获取单条数据
data = cursor.fetchone()

print("Database version: {0}".format(data))

# SQL 插入语句
sql = "insert into linkuser (id, departname, email, flag, job, phonenumber, sex, username) " \
      "values (%s, %s, %s, %s, %s, %s, %s, %s)"
param = ("10003", "政法部", "10003@qq.com", 1, "Java工程师", "18382306297", "女", "李四")

print(sql)
try:
    # 执行sql语句
    cursor.execute(sql, param)
    # 提交到数据库执行
    db.commit()
except Exception as e:
    # 如果发生错误则回滚
    print("SQL执行异常: {}".format(e))
    db.rollback()

sql = "select * from linkuser"
try:
    cursor.execute(sql)
    result = cursor.fetchall()
    for row in result:
        fid = row[0]
        fdepartname = row[1]
        femail = row[2]
        fflag = row[3]
        fjob = row[4]
        fphonenumber = row[5]
        fsex = row[6]
        fusername = row[7]
        print(
            "fid={0}, fdepartname={1}, femail={2}, fflag={3}, fjob={4}, fphonenumber={5}, fsex={6}, fusername={7}".format(
                fid, fdepartname, femail, fflag, fjob, fphonenumber, fsex, fusername))
except Exception as e:
    print("Error！{}".format(e))

# 关闭数据库连接
db.close()
