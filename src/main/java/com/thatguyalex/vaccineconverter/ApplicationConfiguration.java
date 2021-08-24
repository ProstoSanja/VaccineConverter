package com.thatguyalex.vaccineconverter;

import com.thatguyalex.vaccineconverter.aplication.ProcessRawPass;
import com.thatguyalex.vaccineconverter.infrasctructure.apple.ApplePassProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.google.auth.GoogleAuth;
import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GooglePassesClient;
import com.thatguyalex.vaccineconverter.infrasctructure.google.passes.GoogleLoyaltyRepository;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassCertificateProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassNameProvider;
import com.thatguyalex.vaccineconverter.infrasctructure.greenpass.GreenPassRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

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
    GreenPassRepository greenPassRepository(
            GreenPassCertificateProvider greenPassCertificateProvider,
            GreenPassNameProvider greenPassNameProvider
    ) {
        return new GreenPassRepository(greenPassCertificateProvider, greenPassNameProvider);
    }


    @Bean
    ProcessRawPass processRawPass(
        GreenPassRepository greenPassRepository,
        GoogleLoyaltyRepository googleLoyaltyRepository,
        ApplePassProvider applePassProvider
    ) {
        return new ProcessRawPass(greenPassRepository, googleLoyaltyRepository, applePassProvider);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

}
