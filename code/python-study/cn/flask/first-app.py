from flask import Flask, request
from flask import abort, redirect, url_for
from werkzeug.utils import secure_filename

app = Flask(__name__)
app.config['DEBUG'] = True


# 路由
@app.route('/home')
def home():
    return '<h1>Hello！This is My First Flask App</h1>'


@app.route('/hello')
def hello():
    return 'hello'


# 路径变量
@app.route('/user/<username>')
def show_user_profile(username):
    return 'User {0}'.format(username)


@app.route('/post/<int:post_id>')
def show_post(post_id):
    return "Port is {0:d}".format(post_id)


# HTTP方法
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        # form属性是一个字典
        username = request.form['username']
        password = request.form['password']
        return 'POST提交，你的姓名是：{0}, 你的密码是：{1}'.format(username, password)
    elif request.method == 'GET':
        # 使用args属性获取GET方法传送过来的数据, 这个属性也是一个字典
        username = request.args.get('username')
        password = request.args.get('password')
        return 'GET提交，你的姓名是：{0}, 你的密码是：{1}'.format(username, password)


# 文件上传
# 只需要利用 request 的files属性即可，这也是一个字典，包含了被上传的文件
# 如果想获取上传的文件名，可以使用filename属性，不过需要注意这个属性可以被客户端更改，所以并不可靠。
# 更好的办法是利用werkzeug提供的secure_filename方法来获取安全的文件名

@app.route('/upload', methods=['POST'])
def upload_file():
    if request.method == 'POST':
        f = request.files['the_file']
        f.save('/var/www/uploads/' + secure_filename(f.filename))


# Cookies
@app.route('/')
def index():
    username = request.cookies.get('username')
    if username is None:
        return '你现在处于未登录状态'
    return username


# 重定向和错误
@app.route('/index2')
def index2():
    return redirect(url_for('index'))


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
