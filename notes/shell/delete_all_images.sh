#!/bin/bash

set -e

# 检查参数
if [ $# -ne 1 ]; then
    echo "使用方法: $0 <IMAGE_NAME>"
    echo "例如: $0 yiqixing-test"
    exit 1
fi

IMAGE_NAME=$1

docker rmi $(docker images | grep "$IMAGE_NAME" | awk '{print $3}')
