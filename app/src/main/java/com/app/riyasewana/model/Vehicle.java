package com.app.riyasewana.model;

public class Vehicle {
    private String id;
    private String model;
    private String condition;
    private String category;
    private String year;
    private String manufacturer;
    private String transmission;
    private String price;
    private String moreinfo;
    private String img1;
    private String img2;
    private String img3;
    private String name;
    private String contact;
    private String address;

    public Vehicle(){
    }

    public Vehicle(String id, String model, String condition, String category, String year, String manufacturer, String transmission, String price, String moreinfo, String img1, String img2, String img3, String name, String contact, String address) {
        this.id = id;
        this.model = model;
        this.condition = condition;
        this.category = category;
        this.year = year;
        this.manufacturer = manufacturer;
        this.transmission = transmission;
        this.price = price;
        this.moreinfo = moreinfo;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMoreinfo() {
        return moreinfo;
    }

    public void setMoreinfo(String moreinfo) {
        this.moreinfo = moreinfo;
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
}
