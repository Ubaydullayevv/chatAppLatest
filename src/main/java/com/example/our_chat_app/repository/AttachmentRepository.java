package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByOriginalFileName(String filename);
}
