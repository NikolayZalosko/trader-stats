package com.nickz.traderstats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraderBasicInfoDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Double rating;
}
