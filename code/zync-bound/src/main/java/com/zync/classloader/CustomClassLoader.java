package com.zync.classloader;

import org.apache.commons.codec.binary.Base64;

/**
 * @author luocong
 * @description 自定义类加载器
 * @date 2020/5/25 16:00
 */
public class CustomClassLoader extends ClassLoader {

    public static final String CLASS_DATA = "yv66vgAAADQANwoADgAXCQAYABkIABoKABsAHAoADgAdCgAeAB8KABsAIAcAIQoACAAXCAAiCgAIACMKAAgAJAcAJQcAJgEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAVoZWxsbwEAJihMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmc7AQAKU291cmNlRmlsZQEAFENsYXNzTG9hZGVyVGVzdC5qYXZhDAAPABAHACcMACgAKQEAFUNsYXNzTG9hZGVyVGVzdC4uLi4uLgcAKgwAKwAsDAAtAC4HAC8MADAAMQwAKwAyAQAXamF2YS9sYW5nL1N0cmluZ0J1aWxkZXIBAAZoZWxsbyAMADMANAwANQA2AQAfY29tL2NsYXNzbG9hZGVyL0NsYXNzTG9hZGVyVGVzdAEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBAAhnZXRDbGFzcwEAEygpTGphdmEvbGFuZy9DbGFzczsBAA9qYXZhL2xhbmcvQ2xhc3MBAA5nZXRDbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBABUoTGphdmEvbGFuZy9PYmplY3Q7KVYBAAZhcHBlbmQBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsBAAh0b1N0cmluZwEAFCgpTGphdmEvbGFuZy9TdHJpbmc7ACEADQAOAAAAAAACAAEADwAQAAEAEQAAAD4AAgABAAAAGiq3AAGyAAISA7YABLIAAiq2AAW2AAa2AAexAAAAAQASAAAAEgAEAAAABQAEAAYADAAHABkACAABABMAFAABABEAAAAsAAIAAgAAABS7AAhZtwAJEgq2AAsrtgALtgAMsAAAAAEAEgAAAAYAAQAAAAsAAQAVAAAAAgAW";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = getClassData();
        return defineClass(name, b, 0, b.length);
    }

    private byte[] getClassData() {
        return Base64.decodeBase64(CLASS_DATA);
    }
}
