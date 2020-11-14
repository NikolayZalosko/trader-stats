package com.nickz.traderstats.dto;

import com.nickz.traderstats.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraderWithUserAttachedDto {
    private String firstName;
    private String lastName;
    private User user;
}
