import requests

response = requests.get('http://tieba.baidu.com/f?kw=%E5%AD%99%E5%85%81%E7%8F%A0')
response.encoding = 'utf-8'
print(response.text)

