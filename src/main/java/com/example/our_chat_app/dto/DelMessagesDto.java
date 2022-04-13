package com.example.our_chat_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DelMessagesDto {
    @NotEmpty(message = "you must select message for delete")
    List<Long> ids = new ArrayList<>();
    Boolean deleteForEveryone = false;
}