package com.example.our_chat_app.service;

import com.example.our_chat_app.dto.AttachmentDto;
import com.example.our_chat_app.entity.Attachment;
import com.example.our_chat_app.entity.AttachmentContent;
import com.example.our_chat_app.repository.AttachmentRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

public class AttachmentService {


    @Autowired
    private AttachmentRepository attachmentRepo;

    public List<Attachment> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Attachment> pagedResult = attachmentRepo.findAll(paging);
        return pagedResult.toList();
    }

    public String uploadFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        Random random = new Random();
        Attachment attachment = new Attachment();
        try {
            attachment = new Attachment(
                    null,
                    LocalDateTime.now(),
                    attachment.getFilePath(),
                    String.format("%s%s", System.currentTimeMillis(), random.nextInt(100000)) + "." + extension,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize(),
                    new AttachmentContent(file.getBytes())
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileDownloadUri = "";
        try {
            LocalDateTime uploadTime = attachment.getUploadTime();
            int year = uploadTime.getYear();
            int month = uploadTime.getMonthValue();
            int day = uploadTime.getDayOfMonth();
            File file5 = new File(attachment.getFilePath() + "\\uploads");
            if (!file5.exists()) {
                file5.mkdir();
            }
            File file1 = new File(file5 + "\\" + year);
            if (!file1.exists()) {
                file1.mkdir();
            }
            File file2 = new File(file1 + "\\" + month);
            if (!file2.exists()) {
                file2.mkdir();
            }
            File file3 = new File(file2 + "\\" + day);
            if (!file3.exists()) {
                file3.mkdir();
            }
            String newPath = file3 + "\\" + attachment.getGeneratedFileName();
            file.transferTo(new File(newPath));
            attachment.setFilePath(newPath);
            attachmentRepo.save(attachment);

            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(attachment.getOriginalFileName())
                    .toUriString();
        } catch (IOException e) {
        }
        return fileDownloadUri;
    }

    public List<AttachmentDto> getAllFiles(int pageNo, int pageSize) {
        List<Attachment> paginated = findPaginated(pageNo, pageSize);
        List<AttachmentDto> fileDtoList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        String fileDownloadUri = "";
        for (Attachment attachment : paginated) {
            long size = attachment.getSize();
            long sizeInKb = size / 1024;
            String formatDateTime = attachment.getUploadTime().format(formatter);

            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(attachment.getOriginalFileName())
                    .toUriString();
            fileDtoList.add(new AttachmentDto(attachment.getOriginalFileName(), sizeInKb, formatDateTime, fileDownloadUri));

        }
        return fileDtoList;
    }
    public Attachment uploadFileForDistributor(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        Random random = new Random();
        Attachment attachment = new Attachment();
        try {
            attachment = new Attachment(
                    null,
                    LocalDateTime.now(),
                    attachment.getFilePath(),
                    String.format("%s%s", System.currentTimeMillis(), random.nextInt(100000)) + "." + extension,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize(),
                    new AttachmentContent(file.getBytes())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Attachment savedAttachment=null;
        LocalDateTime uploadTime = attachment.getUploadTime();
        int year = uploadTime.getYear();
        int month = uploadTime.getMonthValue();
        int day = uploadTime.getDayOfMonth();
        File file5 = new File(attachment.getFilePath() + "\\distributorsAvatars");
        if (!file5.exists()) {
            file5.mkdir();
        }
        File file1 = new File(file5 + "\\" + year);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File file2 = new File(file1 + "\\" + month);
        if (!file2.exists()) {
            file2.mkdir();
        }
        File file3 = new File(file2 + "\\" + day);
        if (!file3.exists()) {
            file3.mkdir();
        }
        String newPath = file3 + "\\" + attachment.getGeneratedFileName();
        attachment.setFilePath(newPath);
        savedAttachment = attachmentRepo.save(attachment);


        return savedAttachment;
    }

}

