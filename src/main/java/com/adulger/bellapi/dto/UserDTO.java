package com.adulger.bellapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by adulger on 27.12.2021
 **/
public class UserDTO {
    @Getter
    @Setter
    private String userUniqueId;

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String fcmToken;
}
