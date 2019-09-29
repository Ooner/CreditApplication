package com.kocfinans.creditApplication.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public  static void checkEmptyString(String s, String message){
        if (s == null){
            throw  new IllegalArgumentException(message+" bilgisi girilmelidir.");
        }
        if (StringUtils.isEmpty(s.trim())){
            throw  new IllegalArgumentException(message+" bilgisi boş olamaz.");
        }
    }
}
