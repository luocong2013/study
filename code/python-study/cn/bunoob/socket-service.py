# socket 服务端
import socket
import os

# 创建socket对象
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 获取本地主机名
host = socket.gethostname()

port = 9000

# 绑定端口
serversocket.bind((host, port))

# 设置最大连接数，超过后排队
serversocket.listen(5)

while True:
    # 建立客户端连接
    clientsocket, addr = serversocket.accept()
    print('连接地址：{0}'.format(addr))
    msg = '欢迎访问!' + os.linesep
    clientsocket.send(msg.encode('UTF-8'))
    clientsocket.close()
