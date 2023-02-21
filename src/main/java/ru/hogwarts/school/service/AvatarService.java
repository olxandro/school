package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("{avatars.dir.path}")
    private String avatarsDir;

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    private AvatarRepository avatarRepository;
    private StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadCover(Long studentId, MultipartFile file) throws IOException {
        logger.warn("Method uploadCover was called");
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." +
                getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("Method findAvatar was called");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtension(String fileName) {
        logger.info("Method getExtension was called");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAllAvatarsPage(Integer pageNumber, Integer pageSize) {
        logger.info("Method getAllAvatarsPage was called");
        PageRequest pageRequest = PageRequest.of(pageNumber- 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
