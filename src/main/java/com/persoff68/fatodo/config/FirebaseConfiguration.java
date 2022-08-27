package com.persoff68.fatodo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {
    private static final String FIREBASE_APP_NAME = "fatodo";

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream firebaseInputStream = new ClassPathResource("firebase-service-account.json").getInputStream();
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(firebaseInputStream);
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.getApps().stream()
                .filter(ap -> ap.getName().equals(FIREBASE_APP_NAME))
                .findFirst()
                .orElse(FirebaseApp.initializeApp(firebaseOptions, FIREBASE_APP_NAME));

        return FirebaseMessaging.getInstance(app);
    }

}
