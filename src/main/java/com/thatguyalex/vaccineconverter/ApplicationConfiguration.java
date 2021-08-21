package com.thatguyalex.vaccineconverter;

import com.thatguyalex.vaccineconverter.aplication.ProcessRawPass;
import com.thatguyalex.vaccineconverter.infrasctructure.google.auth.GoogleAuth;
import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GooglePassesClient;
import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GoogleLoyaltyRepository;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassCertificateProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    GoogleAuth googleAuth() {
        return new GoogleAuth();
    }

    @Bean
    GooglePassesClient googlePassesClient(GoogleAuth googleAuth) {
        return new GooglePassesClient(googleAuth);
    }

    @Bean
    GoogleLoyaltyRepository loyaltyRepository(GooglePassesClient googlePassesClient) {
        return new GoogleLoyaltyRepository(googlePassesClient);
    }


    @Bean
    GreenPassCertificateProvider greenPassCertificateProvider() {
        return new GreenPassCertificateProvider();
    }

    @Bean
    GreenPassRepository greenPassRepository(GreenPassCertificateProvider greenPassCertificateProvider) {
        return new GreenPassRepository(greenPassCertificateProvider);
    }


    @Bean
    ProcessRawPass processRawPass(GreenPassRepository greenPassRepository, GoogleLoyaltyRepository googleLoyaltyRepository) {
        return new ProcessRawPass(greenPassRepository, googleLoyaltyRepository);
    }

}
