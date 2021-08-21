package com.thatguyalex.vaccineconverter.infrasctructure.google.auth;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.SneakyThrows;


public class GoogleAuth {

    private final ServiceAccountCredentials googleCredentials;

    @SneakyThrows
    public GoogleAuth() {
        googleCredentials = (ServiceAccountCredentials) GoogleCredentials
                .fromStream(this.getClass().getResourceAsStream("/credentials/apikey.json"))
                .createScoped("https://www.googleapis.com/auth/wallet_object.issuer");
    }

    @SneakyThrows
    public ServiceAccountCredentials getCredentials() {
        googleCredentials.refreshIfExpired();
        return googleCredentials;
    }

}
