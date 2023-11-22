package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/*
 * 通用接口
 * */
@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    /*
     * 图片上传
     * */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String objectName;
            if (originalFilename != null) {
                String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
                objectName = UUID.randomUUID().toString() + substring;
            } else {
                objectName = UUID.randomUUID().toString() + "jpg";
            }
            aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(objectName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
