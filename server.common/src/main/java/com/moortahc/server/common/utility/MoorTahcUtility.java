package com.moortahc.server.common.utility;

import java.lang.reflect.Field;

public class MoorTahcUtility {
    
    public static boolean checkSameFieldValues(Object object1, Object object2) throws IllegalAccessException {
        for (Field field1 : object1.getClass().getDeclaredFields()) {
            field1.setAccessible(true);
            for (Field field2 : object2.getClass().getDeclaredFields()) {
                field2.setAccessible(true);
                if (field1.getName().equals(field2.getName())) {
                    if (field1.get(object1) != field2.get(object2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
