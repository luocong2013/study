import pyttsx3

engine = pyttsx3.init()
texts = {
    "中文": "你好，世界",
    "English": "Hello, World",
    "日本語": "こんにちは、世界"
}

for lang, text in texts.items():
    print(f"正在播放{lang}")
    engine.say(text)
    engine.runAndWait()
