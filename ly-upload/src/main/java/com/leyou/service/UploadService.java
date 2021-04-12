package com.leyou.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname UploadService
 * @Description TODO
 * @Date 2021/3/3 15:44
 * @Created by MingChao Hao
 */

public interface UploadService {
    String uploadImage(MultipartFile file);
}
