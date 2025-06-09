package com.sky.controller.admin;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;
    
    @PostMapping("/upload")
    public Result<String> uplode(MultipartFile file) {
        log.info("文件上传：{}", file);
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + extension;
        String filePath = null;
        try {
            filePath = aliOssUtil.upload(file.getBytes(),fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("文件名称：{}，文件路径：{}", fileName, filePath);
        return Result.success(fileName);
    }
}