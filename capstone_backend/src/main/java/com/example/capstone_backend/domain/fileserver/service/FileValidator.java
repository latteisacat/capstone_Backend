package com.example.capstone_backend.domain.fileserver.service;

import com.example.capstone_backend.domain.fileserver.exception.InvalidImageTypeException;
import com.example.capstone_backend.domain.fileserver.exception.InvalidVideoTypeException;
import com.example.capstone_backend.domain.fileserver.exception.NotFoundImageException;
import com.example.capstone_backend.domain.fileserver.exception.NotFoundVideoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileValidator {


    private static final List<String> IMAGE_FILE_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif", "bmp", "tiff");
    private static final List<String> VIDEO_FILE_EXTENSIONS = List.of("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm");

    public void validateImageFile(final MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new NotFoundImageException();
        }

        if (!IMAGE_FILE_EXTENSIONS.contains(getFileExtension(image))) {
            throw new InvalidImageTypeException();
        }
    }

    public void validateVideoFile(final MultipartFile video) {
        if (video == null || video.isEmpty()) {
            throw new NotFoundVideoException();
        }

        if (!VIDEO_FILE_EXTENSIONS.contains(getFileExtension(video))) {
            throw new InvalidVideoTypeException();
        }
    }

    private String getFileExtension(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        return Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".")).substring(1).toLowerCase();
    }
}
