import urllib.request
import requests

'''
爬取网易云音乐
'''


def write_lyric(song_name, lyric):
    print('正在写入歌曲：{}'.format(song_name))
    with open('/home/luoc/Music/CloudMusic/lyric/{}.txt'.format(song_name), 'a', encoding='UTF-8') as f:
        f.write(lyric)


def download_song(song_name, song_id):
    singer_url = 'http://music.163.com/song/media/outer/url?id={}.mp3'.format(song_id)
    print('正在下载歌曲：{}'.format(song_name))
    urllib.request.urlretrieve(singer_url, '/home/luoc/Music/CloudMusic/{}.mp3'.format(song_name))


if __name__ == '__main__':
    singer_url = 'http://music.163.com/song/media/outer/url?id={}.mp3'.format('202373')
    response = requests.get(singer_url)
    buff = response.content
    with open('/home/luoc/Music/CloudMusic/{}.mp3'.format('南方姑娘 - 赵雷'), 'wb') as f:
        f.write(buff)
