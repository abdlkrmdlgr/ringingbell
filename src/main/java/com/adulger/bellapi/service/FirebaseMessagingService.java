package com.adulger.bellapi.service;

import com.adulger.bellapi.model.FirebaseNote;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

/**
 * Created by adulger on 26.12.2021
 **/
@Service
public class FirebaseMessagingService {

    public String sendNotification(FirebaseNote note, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();


//        FirebaseInstanceId.getInstance().getToken();
        return FirebaseMessaging.getInstance().send(message);
    }





}


