package vn.iotstar.Service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.Service.IFileUploadService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements IFileUploadService {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Override
    public String uploadFile(MultipartFile file, String uploadDir) {
        if (file.isEmpty()) {
            return null;
        }
        
        try {
            // Tạo thư mục nếu chưa tồn tại
            Path uploadDirPath = Paths.get(uploadDir);
            if (!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }
            
            // Tạo tên file unique
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + fileExtension;
            
            // Lưu file
            Path filePath = uploadDirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
    
    @Override
    public boolean deleteFile(String fileName, String uploadDir) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
    
    @Override
    public String getFileUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "/images/default-icon.png";
        }
        return "/uploads/" + fileName;
    }
}
