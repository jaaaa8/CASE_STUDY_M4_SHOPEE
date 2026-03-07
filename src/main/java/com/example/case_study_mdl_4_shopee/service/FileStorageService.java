package com.example.case_study_mdl_4_shopee.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String saveFile(MultipartFile file) throws IOException {
        // 1. Khai báo Path chuẩn của Java NIO
        Path root = Paths.get(uploadPath);

        // 2. Tạo thư mục nếu chưa tồn tại
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        // 3. Tạo tên file duy nhất (Dùng UUID để tránh trùng tên ảnh)
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // 4. Lưu file vào thư mục (Thêm StandardCopyOption.REPLACE_EXISTING để an toàn)
        Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        return fileName; // Trả về tên file để lưu vào Database
    }
}
