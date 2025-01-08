import pyttsx3

def batch_speak(text_list):
    engine = pyttsx3.init()
    for text in text_list:
        print(f"正在朗读: {text}")
        engine.say(text)
    engine.runAndWait()

# 测试
texts = [
    "第一段话",
    "第二段话",
    "第三段话"
]
batch_speak(texts)