package com.adulger.bellapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by adulger on 25.12.2021
 **/
@Entity
public class BellNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Setter
    @JoinColumn(name = "bell_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bell bell;

    @Getter
    @Setter
    private Date ringingTime;
}
