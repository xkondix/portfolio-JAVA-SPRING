package com.kowalczyk_konrad.portfolio.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initialize() {

        try {
            FileInputStream serviceAccount =
                    new FileInputStream("./portfolio-a6fe4-firebase-adminsdk-7z3ar-d7004d9093.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://portfolio-a6fe4.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
