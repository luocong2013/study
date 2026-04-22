package com.mountain.common.holder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 加解密工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/22 14:17
 **/
@Component
@RequiredArgsConstructor
public class EncryptionDecryptionHolder {

    private final RedisCacheHolder redisCacheHolder;
}
