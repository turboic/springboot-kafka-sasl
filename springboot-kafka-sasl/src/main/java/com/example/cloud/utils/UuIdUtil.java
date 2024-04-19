package com.example.cloud.utils;

import java.util.UUID;

/**
 * @author root
 */
public class UuIdUtil {
    public static String generateId(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
