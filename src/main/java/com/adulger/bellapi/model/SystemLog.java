package com.adulger.bellapi.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by adulger on 26.12.2021
 **/
@Entity
public class SystemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Date loggingDate;

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private Long bellId;
}
