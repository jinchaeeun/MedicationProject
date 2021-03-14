package com.example.medicationproject;

public class FamilyAddress {
    //Member variable --------------------------
    private String name;
    private String phone;
    private String email;

    //Constructor Method ----------------------- 멤버 변수 초기화하는 메서드
    //Code (alt+c) - generate - Constructor 클릭 초기화시킬 변수 선택하면 자동 생성
    //           alt + insert - Constructor 도 가능
    // 생성자
    public FamilyAddress(){

    }

    public FamilyAddress(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }


    //Custom Method -------------------------
    public String getInfo(){        //String 문자열 name, phone, email 다 넘어가도록 getInfo()라는 함수를 만듦
        return this.name+ " - " + this.phone + " - "+this.email;
    }

    @Override
    public String toString() {
        return "FamilyAddress{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}