package com.example.Event_Management.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUploadUtil {
    public static String saveFiles(String uploadDir, MultipartFile file) throws IOException {
        System.out.println("UPLOAD DIR (saveFiles): " + new File(uploadDir).getAbsolutePath());
        List<String> filePaths = new ArrayList<>();

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();


        if (file==null||file.isEmpty()) {
            return null;
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());





        return uploadDir+"/"+fileName;
    }
}
