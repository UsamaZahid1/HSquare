package com.example.hsquare.Model;

public class Products {
    private String pid,pname, description, price, image, category;

    public Products() {

    }

    public Products(String pid, String pname, String description, String price, String image, String category) {
        this.pid = pid;
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
