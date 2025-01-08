import pyttsx3

engine = pyttsx3.init()
text = "这段话将被保存为音频文件"
# 保存为mp3
engine.save_to_file(text, 'output.mp3')
engine.runAndWait()