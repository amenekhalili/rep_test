package ir.fararayaneh.erp.utils;

import java.util.Random;
import java.util.UUID;

public class UUIDHelper {
    public static String getProperUUID(){
        return UUID.randomUUID().toString().replace("-", "0");
    }

    public static int getRandomInteger(int bound){
        return   new Random().nextInt(bound);
    }
}
