package com.crusos.domain.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
    String storeFile(MultipartFile file);
}
