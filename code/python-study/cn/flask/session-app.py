# Sessions
# 我们可以使用全局对象session来管理用户会话。Sesison 是建立在 Cookie 技术上的，不过在 Flask 中，
# 我们还可以为 Session 指定密钥，这样存储在 Cookie 中的信息就会被加密，从而更加安全
from flask import Flask, session, redirect, url_for, escape, request

app = Flask(__name__)


@app.route('/')
def index():
    if 'username' in session:
        return 'Logged in as {0}'.format(escape(session['username']))
    return 'You are not logged in'


@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        session['username'] = request.form['username']
        return redirect(url_for('index'))
    elif request.method == 'GET':
        session['username'] = request.args.get('username')
        return redirect(url_for('index'))


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5001)
