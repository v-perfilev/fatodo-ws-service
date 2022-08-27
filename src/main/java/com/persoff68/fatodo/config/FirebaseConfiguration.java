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
import java.util.List;
import java.util.Optional;

@Configuration
public class FirebaseConfiguration {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        FirebaseApp firebaseApp = Optional.ofNullable(findExistingFirebaseApp())
                .orElse(createNewFirebaseApp());
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    private static FirebaseApp findExistingFirebaseApp() {
        FirebaseApp firebaseApp = null;

        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        if (firebaseApps != null && !firebaseApps.isEmpty()) {
            for (FirebaseApp app : firebaseApps) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                    firebaseApp = app;
            }
        }

        return firebaseApp;
    }

    private static FirebaseApp createNewFirebaseApp() throws IOException {
        FirebaseApp firebaseApp;
        InputStream firebaseInputStream = new ClassPathResource("firebase-service-account.json").getInputStream();
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(firebaseInputStream);
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        return firebaseApp;
    }

}
