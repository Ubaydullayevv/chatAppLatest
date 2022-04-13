package com.example.our_chat_app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(nullable = false)

    private LocalDateTime uploadTime;
    @Column(nullable = false)

    private String filePath="./src/main/resources"; /// boshiga nuqta qo'yilganini sababi u sizning xoxlagan yo'lingiz bo'lsa ham (.) degani shu projectning absolut pathi
    @Column(nullable = false)
    String generatedFileName;

    @Column(nullable = false)
    String originalFileName;

    @Column(nullable = false)
    String contentType;

    @Column(nullable = false)
    Long size;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
     AttachmentContent attachmentContent;

    public Attachment(String generatedFileName, String originalFileName, String contentType, Long size, AttachmentContent attachmentContent) {
        this.generatedFileName = generatedFileName;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.size = size;
        this.attachmentContent = attachmentContent;
    }

    public static Attachment prepareAttachment(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            if (2 > split.length) {
                return null;
            }
            String generatedFileName = UUID.randomUUID() + "." + split[split.length - 1];
            return new Attachment(
                    generatedFileName,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize(),
                    new AttachmentContent(file.getBytes()));
        }
        return null;
    }


}
