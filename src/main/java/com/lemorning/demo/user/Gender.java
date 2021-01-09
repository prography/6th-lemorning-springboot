package com.lemorning.demo.user;

public enum Gender {
    MALE("male"),FEMALE("female");

    String value;
    Gender(String val){
        this.value = val;
    }
    public String getValue(){
        return value;
    }
}