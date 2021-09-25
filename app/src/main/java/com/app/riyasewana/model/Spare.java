package com.app.riyasewana.model;

public class Spare {
    private String id;
    private String name;
    private String contact;
    private String address;
    private String title;
    private String condition;
    private String price;
    private String additional;
    private String img1;
    private String img2;
    private String img3;

    public Spare() {
    }

    public Spare(String id, String name, String contact, String address, String title, String condition, String price, String additional, String img1, String img2, String img3) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.title = title;
        this.condition = condition;
        this.price = price;
        this.additional = additional;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }
}
