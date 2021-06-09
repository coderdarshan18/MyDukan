package com.india.mydukan.Models;

public class CategoryModel {
    public  CategoryModel(){

    }

    private int cat_image;
    private String cat_title;

    public CategoryModel(int cat_image, String cat_title) {
        this.cat_image = cat_image;
        this.cat_title = cat_title;
    }

    public int getCat_image() {
        return cat_image;
    }

    public void setCat_image(int cat_image) {
        this.cat_image = cat_image;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }
}

