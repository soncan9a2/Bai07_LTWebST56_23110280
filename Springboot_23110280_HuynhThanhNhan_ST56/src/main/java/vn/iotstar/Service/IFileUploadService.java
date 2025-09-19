package vn.iotstar.Service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    String uploadFile(MultipartFile file, String uploadPath);
    boolean deleteFile(String fileName, String uploadPath);
    String getFileUrl(String fileName);
}
