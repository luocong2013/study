import random
import string


# 创建随机强密码

def generate_strong_password(length=12):
    characters = string.ascii_letters + string.digits + string.punctuation
    password = ''.join(random.choice(characters) for _ in range(length))
    print(f"生成的强密码: {password}")
    return password


generate_strong_password(16)
