package com.india.mydukan.RetrofitApi;

import com.google.gson.annotations.SerializedName;
import com.india.mydukan.Models.BannerModel;
import com.india.mydukan.Models.CategoryModel;
import com.india.mydukan.Models.PlateModel;

import java.util.List;

public class Users {
    @SerializedName("response")
    private  String Response;

    @SerializedName("user_id")
    private String UserId;

    @SerializedName("categories")
    private List<CategoryModel> category;

    @SerializedName("banners")
    private List<BannerModel> banners;

    @SerializedName("plates")
    private List<PlateModel> plates;

    public Users(String response, String userId, List<CategoryModel> category, List<BannerModel> banners, List<PlateModel> plates) {
        Response = response;
        UserId = userId;
        this.category = category;
        this.banners = banners;
        this.plates = plates;
    }

    public String getResponse() {
        return Response;
    }

    public String getUserId() {
        return UserId;
    }

    public List<CategoryModel> getCategory() {
        return category;
    }

    public List<BannerModel> getBanners() {
        return banners;
    }

    public List<PlateModel> getPlates() {
        return plates;
    }
}
