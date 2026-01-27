package org.mehmood.blogapplicationbackendproject.impl;

import org.mehmood.blogapplicationbackendproject.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!file.getContentType().startsWith("image")) {
            throw new IllegalArgumentException("Only image files allowed");
        }

        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        String filepath = path + File.separator + filename;
        Files.copy(file.getInputStream(), Paths.get(filepath), StandardCopyOption.REPLACE_EXISTING);
        return filename;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}