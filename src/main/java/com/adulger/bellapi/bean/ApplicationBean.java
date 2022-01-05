package com.adulger.bellapi.bean;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by adulger on 26.12.2021
 **/
@Repository
public class ApplicationBean {
    public ApplicationBean() throws IOException {
        initFirebase();
    }

    void initFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream(new ClassPathResource("bellring-1ba53-firebase-adminsdk-hcnnw-475392f6d8.json").getFile());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
