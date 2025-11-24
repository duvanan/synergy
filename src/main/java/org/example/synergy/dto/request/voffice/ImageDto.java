package org.example.synergy.dto.request.voffice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDto {
    String staffImageSignId;
    String path;
    String name;
    String fromDateActive;
    String toDateActive;
    String status;
    String storage;
    String staffIdVof2;
    String creatorIdVof2;
    String type;
}
