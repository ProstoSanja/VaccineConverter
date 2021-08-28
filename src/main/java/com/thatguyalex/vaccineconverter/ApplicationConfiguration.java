package com.thatguyalex.vaccineconverter;

import com.thatguyalex.vaccineconverter.aplication.ProcessRawPass;
import com.thatguyalex.vaccineconverter.infrasctructure.apple.ApplePassProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.google.auth.GoogleAuth;
import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GooglePassesClient;
import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GoogleLoyaltyRepository;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassCertificateProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassIssuerProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassNameProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    ApplePassProvider applePassProvider() {
        return new ApplePassProvider();
    }

    @Bean
    GreenPassCertificateProvider greenPassCertificateProvider() {
        return new GreenPassCertificateProvider();
    }

    @Bean
    GreenPassNameProvider greenPassNameProvider() {
        return new GreenPassNameProvider();
    }

    @Bean
    GreenPassIssuerProvider greenPassIssuerProvider() {
        return new GreenPassIssuerProvider();
    }

    @Bean
    GreenPassRepository greenPassRepository(
            GreenPassCertificateProvider greenPassCertificateProvider,
            GreenPassNameProvider greenPassNameProvider,
            GreenPassIssuerProvider greenPassIssuerProvider
    ) {
        return new GreenPassRepository(greenPassCertificateProvider, greenPassNameProvider, greenPassIssuerProvider);
    }


    @Bean
    ProcessRawPass processRawPass(
        GreenPassRepository greenPassRepository,
        GoogleLoyaltyRepository googleLoyaltyRepository,
        ApplePassProvider applePassProvider
    ) {
        return new ProcessRawPass(greenPassRepository, googleLoyaltyRepository, applePassProvider);
    }

    @Profile("development")
    @Configuration
    class DevelopmentApplicationConfiguration implements WebMvcConfigurer {
        @Profile("development")
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }


}
