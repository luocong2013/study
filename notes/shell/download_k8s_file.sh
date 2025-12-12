#!/bin/bash

set -e

# 检查参数
if [ $# -lt 1 ]; then
    echo "使用方法: $0 <TARGET_CONTAINER> [<TARGET_CONTEXT:context-prd>] [<TARGET_NAMESPACE:yiqixing-prd>] [<TARGET_FILENAME_EXTENSION:.hprof>]"
    echo "例如1: $0 yiqixing-user"
    echo "例如2: $0 yiqixing-user context-dev yiqixing-pre .log"
    exit 1
fi

log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] - $1"
}

log "start"

current_context=$(kubectl config current-context)
target_context="context-prd"
target_namespace="yiqixing-prd"
target_filename_extension=".hprof"
target_container="$1"

if [ -n "$2" ]; then
    target_context="$2"
fi
if [ -n "$3" ]; then
    target_namespace="$3"
fi
if [ -n "$4" ]; then
    target_filename_extension="$4"
fi

log "current_context: ${current_context}"
log "target_context: ${target_context}"
log "target_namespace: ${target_namespace}"
log "target_filename_extension: ${target_filename_extension}"
log "target_container: ${target_container}"

kubectl config use-context ${target_context}

target_pod_name=$(kubectl get pods -n ${target_namespace} | grep ${target_container} | awk '{print $1}')
log "target_pod_name: ${target_pod_name}"

log "kubectl exec -it -n ${target_namespace} ${target_pod_name} -- find logs -maxdepth 1 -type f -name "*${target_filename_extension}" -print0 | tr -d '\r'"
target_file_path=$(kubectl exec -it -n ${target_namespace} ${target_pod_name} -- find logs -maxdepth 1 -type f -name "*${target_filename_extension}" -print0 | tr -d '\r')
log "target_file_path: ${target_file_path}"

log "kubectl cp ${target_namespace}/${target_pod_name}:${target_file_path} ${target_file_path}"
kubectl cp ${target_namespace}/${target_pod_name}:${target_file_path} ${target_file_path}

kubectl config use-context ${current_context}

log "end"