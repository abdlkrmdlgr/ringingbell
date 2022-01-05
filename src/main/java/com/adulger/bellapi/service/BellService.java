package com.adulger.bellapi.service;

import com.adulger.bellapi.dto.BellDTO;
import com.adulger.bellapi.dto.UserDTO;
import com.adulger.bellapi.model.*;
import com.github.javafaker.Faker;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by adulger on 25.12.2021
 **/
@Service
public class BellService extends BaseService {

    @Autowired
    UserService userService;
    @Autowired
    BellServiceImpl bellServiceImpl;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    public void init(int count) {
        List<UserDTO> userList = userService.findAll();
        Random rand = new Random();

        Faker faker = new Faker();
        for (int i = 0; i < count; i++) {
            Bell bell = new Bell();
            bell.setBellUniqueId(UUID.randomUUID().toString());
            bell.setLabel(faker.address().city());
            User user = userService.findBy(userList.get(rand.nextInt(userList.size())).getUserUniqueId());
            bell.setUser(user);

//            BellSetting bellSetting = new BellSetting();
//            bellSetting.setCanBellRing(false);
//            bellSetting.setCanNotificationRecieve(true);
//            bell.setBellSetting(bellSetting);

            for (int j = 0; j < 3; j++) {
                BellNotification bellNotification = new BellNotification();
                bellNotification.setBell(bell);
                bellNotification.setRingingTime(new Date());
                if (bell.getBellNotificationList() == null) {
                    bell.setBellNotificationList(new ArrayList<>());
                }
                bell.getBellNotificationList().add(bellNotification);
            }

            bellServiceImpl.save(bell);
        }
    }

    public BellDTO getBellBy(String bellUniqueId) {
        return bellServiceImpl.findByUniqueId(bellUniqueId, getAuthUser().getId()).toDTO();
    }

    public List<Bell> findAll() {
        return getListOfObjectFromIterable(bellServiceImpl.findAll());
    }

    private List<Bell> getListOfObjectFromIterable(Iterable<Bell> iterable) {
        List<Bell> list = new ArrayList<>();
        iterable.iterator().forEachRemaining(list::add);
        return list;
    }

    public List<BellDTO> findBy() {
        return userService.findBy(getAuthUser().getUserUniqueId()).getBellDTOList();
    }

    public void changeNotificationStatus(String bellUniqueId, boolean status) {
//        WARNING: burada sadece kendi zillerinin bildirimlerini açar kapatır
//        bellServiceImpl.changeNotificationStatus(bellUniqueId, status, getAuthUser().getId());
        System.out.println(bellUniqueId + " -> "+ status);
        bellServiceImpl.changeNotificationStatus(bellUniqueId, status);
    }

    public void bellRinging(String bellUniqueId) throws FirebaseMessagingException {

        Bell bell = bellServiceImpl.getBellBy(getAuthUser().getId(), bellUniqueId);
        String token = getAuthUser().getFcmToken();
        String message = "";

        if (bell.isNotificationEnabled()) {
            message = getBundleString("bell.ringingAndNotificationSend");
            FirebaseNote firebaseNote = new FirebaseNote();
            firebaseNote.setSubject("BellApp");
            firebaseNote.setContent(message);
            firebaseNote.setData(new HashMap<>());

            firebaseMessagingService.sendNotification(
                    firebaseNote,
                    token
            );
        } else {
            message = getBundleString("bell.ringingButNotificationDidntSend");
        }
        SystemLog systemLog = new SystemLog();
        systemLog.setBellId(bell.getId());
        systemLog.setUserId(getAuthUser().getId());
        systemLog.setLoggingDate(new Date());
        systemLog.setContent(message);
        logging(systemLog);
    }

    public void removeBellFromUser(String bellUniqueId) {
        bellServiceImpl.deleteUserBell(bellUniqueId, getAuthUser().getId());
    }
}
