package com.springframework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public void saveImage(Long id, MultipartFile file) {
        log.debug("Save received file in recipe id:" + id);
    }
}
