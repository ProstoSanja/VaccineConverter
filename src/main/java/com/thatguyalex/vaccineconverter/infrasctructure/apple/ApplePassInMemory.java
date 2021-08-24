package com.thatguyalex.vaccineconverter.infrasctructure.apple;

import lombok.Value;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Value
public class ApplePassInMemory {

    public ApplePassInMemory(byte[] data) {
        this.data = data;
        this.expirationTime = Instant.now().plus(1, ChronoUnit.DAYS);
    }

    byte[] data;
    Instant expirationTime;

}
