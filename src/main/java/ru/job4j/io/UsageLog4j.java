package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        boolean check = true;
        int age = 33;
        byte data = 100;
        short flat = 427;
        long weight = 0L;
        double pi = 3.14159265;
        float money = 5.55F;
        char symbol = 999;
        LOG.debug("Check = {}, age = {}, data : {}, flat = {}", check, age, data, flat);
        LOG.debug("weight = {}, pi = {}, money = {}, symbol = {}", weight, pi, money, symbol);
    }
}
