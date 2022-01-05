package com.adulger.bellapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by adulger on 27.12.2021
 **/
public class BellDTO {
    @Getter
    @Setter
    private String bellUniqueId;

    @Getter
    @Setter
    private String label;

    @Getter
    @Setter
    private boolean notificationEnabled;
}
