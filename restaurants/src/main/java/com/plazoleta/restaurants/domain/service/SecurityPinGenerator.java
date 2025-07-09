package com.plazoleta.restaurants.domain.service;

import java.util.Random;

public class SecurityPinGenerator {

    private static final Random random = new Random();
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    public static String generate() {
        int pin = MIN_PIN + random.nextInt(MAX_PIN - MIN_PIN + 1);
        return String.valueOf(pin);
    }
}
