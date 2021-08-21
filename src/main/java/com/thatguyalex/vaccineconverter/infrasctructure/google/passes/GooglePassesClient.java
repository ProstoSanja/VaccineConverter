package com.thatguyalex.vaccineconverter.infrasctructure.google.passes;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.walletobjects.Walletobjects;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.thatguyalex.vaccineconverter.infrasctructure.google.auth.GoogleAuth;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class GooglePassesClient {

    private final GoogleAuth googleAuth;

    @SneakyThrows
    public Walletobjects getClient() {
        return new Walletobjects
                .Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory(), new HttpCredentialsAdapter(googleAuth.getCredentials()))
                .setApplicationName(getClass().getPackageName())
                .build();
    }

    public ServiceAccountCredentials getCredentials() {
        return googleAuth.getCredentials();
    }


}
