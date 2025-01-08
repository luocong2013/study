import pyttsx3

# 创建语音引擎
engine = pyttsx3.init()
# 说话
engine.say("你好，我是Python语音助手")
# 执行
engine.runAndWait()
