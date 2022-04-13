package com.example.our_chat_app.Controller;

import com.example.our_chat_app.dto.AttachmentDto;
import com.example.our_chat_app.entity.Attachment;
import com.example.our_chat_app.entity.AttachmentContent;
import com.example.our_chat_app.repository.AttachmentRepository;
import com.example.our_chat_app.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    AttachmentRepository attachmentRepo;
    @Autowired
    AttachmentService attachmentService;
    String filePath = "./src/main/resources";
    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getAttachment(@PathVariable Long id){
        Attachment attachment = attachmentRepo.findById(id).orElse(null);
        AttachmentContent content = Objects.requireNonNull(attachment).getAttachmentContent();
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getOriginalFileName())
                .body(new ByteArrayResource(content.getData()));
    }


    //1.Save file and return download link
    @PostMapping("/uploadFile")
    public ResponseEntity uploadFileToDb(@RequestParam("file") MultipartFile file) {
        String fileDownloadUri = attachmentService.uploadFile(file);
        return ResponseEntity.ok(fileDownloadUri);
    }

/**
 * 2.return File(pageable)
**/
 @GetMapping("/getFiles/{pageNo}/{pageSize}")
 public ResponseEntity<List<AttachmentDto>> getFiles(@PathVariable int pageNo, @PathVariable int pageSize) {
 List<AttachmentDto> fileDtoList = attachmentService.getAllFiles(pageNo, pageSize);
 return ResponseEntity.ok(fileDtoList);
 }




 @GetMapping("/downloadFile/{fileName:.+}")
 public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {

 Attachment byGeneratedName = attachmentRepo.findByOriginalFileName(fileName);

 Path path = Paths.get(byGeneratedName.getFilePath());
 Resource resource = null;
 try {
 resource = new UrlResource(path.toUri());
 } catch (MalformedURLException e) {
 e.printStackTrace();
 }
 return ResponseEntity.ok()
 .contentType(MediaType.parseMediaType(byGeneratedName.getContentType()))
 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
 .body(resource);

 }

}

