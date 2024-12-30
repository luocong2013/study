import cv2

# imread(filename,flags)
# 第一个参数是图像名称，它需要文件的完全限定路径名
# 第二个参数是一个可选的标志，允许您指定图像的表示方式。OpenCV为该标志提供了几个选项，但最常见的选项包括
# 1. cv2.IMREAD_UNCHANGED or -1
# 2. cv2.IMREAD_GRAYSCALE or 0
# 3. cv2.IMREAD_COLOR or 1
# 标志的默认值为1，它将在图像中读取为彩色图像。当您想以特定格式阅读图像时，只需指定适当的标志即可。

img_path = '/Users/luocong/Downloads/barcode_001.png'
img_grayscale = cv2.imread(img_path, cv2.IMREAD_GRAYSCALE)
cv2.imshow('graycsale image', img_grayscale)
cv2.waitKey(0)
cv2.destroyAllWindows()
cv2.imwrite('grayscale.jpg', img_grayscale)
