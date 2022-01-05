package com.adulger.bellapi.model;

import com.adulger.bellapi.dto.BellDTO;
import com.adulger.bellapi.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adulger on 24.12.2021
 **/
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter @Setter private String userUniqueId;

    @Getter @Setter private String fullName;

    @Getter @Setter private String email;

    @Setter private String password;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bell> bellList;

    @Getter @Setter private String fcmToken;

    public UserDTO toDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserUniqueId(getUserUniqueId());
        userDTO.setEmail(getEmail());
        userDTO.setFullName(getFullName());
        userDTO.setFcmToken(getFcmToken());
        return userDTO;
    }


    public List<BellDTO> getBellDTOList(){
        List<BellDTO> bellDTOList = new ArrayList<>();
        for (Bell bell : getBellList()) {
            bellDTOList.add(bell.toDTO());
        }
        return bellDTOList;
    }


}
