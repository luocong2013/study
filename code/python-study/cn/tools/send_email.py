import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.image import MIMEImage


def send_email(smtp_server, port, from_email, from_email_password, to_email, subject, body):
    msg = MIMEMultipart()
    msg['From'] = from_email
    msg['To'] = to_email
    msg['Subject'] = subject

    # 添加附件正文
    msg.attach(MIMEText(body, 'plain', 'utf-8'))

    try:
        with smtplib.SMTP(smtp_server, port) as server:
            server.starttls()  # 启动TLS加密
            server.login(from_email, from_email_password)  # 登录
            server.sendmail(from_email, to_email, msg.as_string())  # 发送邮件
            server.quit()  # 退出
    except Exception as e:
        print('sendmail failed next is the reason')
        print(e)


send_email('smtp.163.com', 465, 'your_email@example.com', 'your_password',
           'to_email@example.com', 'Hello!', 'This is a test email from Python.')
