package com.zync.ocr.controller;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.zync.ocr.utils.IdUtil;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * ocr识别控制器
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/7/1 16:23
 */
@Slf4j
@RestController
@RequestMapping("/ocr")
public class OcrController {

    /**
     * 图片内容识别
     * @param file
     */
    @PostMapping("/predict/v1")
    public OcrResult predict(@RequestParam("file") MultipartFile file) {
        Path path = Paths.get(Objects.requireNonNull(OcrController.class.getResource("/")).getPath(), IdUtil.randomId() + "_" + file.getOriginalFilename());
        try {
            file.transferTo(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("保存文件出错", e);
        }
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);
        OcrResult result = engine.runOcr(path.toString());
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("删除文件出错", e);
        }
        return result;
    }

}
