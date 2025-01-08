import pyttsx3

engine = pyttsx3.init()

# 获取当前语速
rate = engine.getProperty('rate')
print(f'当前语速：{rate}')
# 设置新语速 (范围一般在100-300之间)
engine.setProperty('rate', 150)

# 获取当前音量
volume = engine.getProperty('volume')
print(f'当前音量：{volume}')
# 设置新音量 (范围为0.0-1.0)
engine.setProperty('volume', 0.8)

# 查看可用声音
voices = engine.getProperty('voices')
for voice in voices:
    print(f'声音ID：{voice.id}')
    print(f'声音名称：{voice.name}')
    print(f'语言：{voice.languages}')
    print('---')

# 设置第一个声音
engine.setProperty('voice', voices[0].id)

# 测试新设置
engine.say("现在我的声音变了！")
engine.runAndWait()
