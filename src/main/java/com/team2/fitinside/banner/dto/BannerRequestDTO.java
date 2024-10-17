package com.team2.fitinside.banner.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BannerRequestDTO {
    private String imageUrl;
    private Integer displayOrder;
    private String title;
}