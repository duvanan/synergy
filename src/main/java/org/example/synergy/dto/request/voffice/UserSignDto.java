package org.example.synergy.dto.request.voffice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignDto {
    UserDto userDto;
    String signLevel;
    String showImage;
    String promulgatingSigner;
    String positionSign;
    String orgId;
    String parallelSignGroup;
}
