package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentDto {
   private String OriginalName;
   private Long sizeKb;
   private String uploadDate;
   private String downloadLink;

}
