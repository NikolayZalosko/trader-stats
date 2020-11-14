package com.nickz.traderstats.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAndTraderCreationDto {
    
    @NotBlank(message = "Trader's first name field can't be blank")
    private String traderFirstName;
    
    @NotBlank(message = "Trader's last name field can't be blank")
    private String traderLastName;
    
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;
    
    @NotBlank(message = "Message field can't be blank")
    private String message;
}
