package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.modules.Film;

@Getter
@Setter
public class FeedbackDto {
    private long feedbackId;
    private String feedbackText;
    private Film filmFk;
}
