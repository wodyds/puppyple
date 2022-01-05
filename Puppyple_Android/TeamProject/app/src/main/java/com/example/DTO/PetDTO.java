package com.example.DTO;

import java.io.Serializable;

public class PetDTO implements Serializable {

    private int pet_id;
    private String member_id, pet_name, pet_breed, pet_age,
            pet_gender, pet_nuetering, pet_weight, pet_filename, pet_filepath;

    //펫정보추가할때 모든 정보를 저장하러간다.
    public PetDTO(int pet_id, String member_id, String pet_name, String pet_breed, String pet_age, String pet_gender, String pet_nuetering, String pet_weight, String pet_filename, String pet_filepath) {
        this.pet_id = pet_id;
        this.member_id = member_id;
        this.pet_name = pet_name;
        this.pet_breed = pet_breed;
        this.pet_age = pet_age;
        this.pet_gender = pet_gender;
        this.pet_nuetering = pet_nuetering;
        this.pet_weight = pet_weight;
        this.pet_filename = pet_filename;
        this.pet_filepath = pet_filepath;
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public String getPet_age() {
        return pet_age;
    }

    public void setPet_age(String pet_age) {
        this.pet_age = pet_age;
    }

    public String getPet_gender() {
        return pet_gender;
    }

    public void setPet_gender(String pet_gender) {
        this.pet_gender = pet_gender;
    }

    public String getPet_nuetering() {
        return pet_nuetering;
    }

    public void setPet_nuetering(String pet_nuetering) {
        this.pet_nuetering = pet_nuetering;
    }

    public String getPet_weight() {
        return pet_weight;
    }

    public void setPet_weight(String pet_weight) {
        this.pet_weight = pet_weight;
    }

    public String getPet_filename() {
        return pet_filename;
    }

    public void setPet_filename(String pet_filename) {
        this.pet_filename = pet_filename;
    }

    public String getPet_filepath() {
        return pet_filepath;
    }

    public void setPet_filepath(String pet_filepath) {
        this.pet_filepath = pet_filepath;
    }
}