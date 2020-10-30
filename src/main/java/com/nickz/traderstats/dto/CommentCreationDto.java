package com.nickz.traderstats.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreationDto {
    private int traderId;
    private int rating;
    private String message;
}
