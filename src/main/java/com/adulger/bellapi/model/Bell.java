package com.adulger.bellapi.model;

import com.adulger.bellapi.dto.BellDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by adulger on 25.12.2021
 **/
@Entity
public class Bell {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String bellUniqueId;

    @Getter
    @Setter
    private String label;

    @Setter
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Getter
    @Setter
    @OneToMany(orphanRemoval=true, mappedBy = "bell", fetch = FetchType.LAZY)
    private List<BellNotification> bellNotificationList;

    @Getter
    @Setter
    private boolean notificationEnabled;

    public BellDTO toDTO() {
        BellDTO bellDTO = new BellDTO();
        bellDTO.setBellUniqueId(getBellUniqueId());
        bellDTO.setLabel(getLabel());
        bellDTO.setNotificationEnabled(isNotificationEnabled());
        return bellDTO;
    }
}
