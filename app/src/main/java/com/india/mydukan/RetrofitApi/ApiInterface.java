package com.india.mydukan.RetrofitApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    /////email register//////
    @GET("email_registration.php")
    Call<Users> performEmailRegistration(
            @Query("user_name") String user_name,
            @Query("user_email") String user_email,
            @Query("user_password") String user_password
    );

    ////email login//////

    @GET("email_login.php")
    Call<Users> performEmailLogin(

            @Query("user_email") String user_email,
            @Query("user_password") String user_password
    );

    /////phone register//////

    @GET("phone_registration.php")
    Call<Users> performPhoneRegistration(
            @Query("user_phone") String user_phone

    );


    @GET("phone_login.php")
    Call<Users> performPhoneLogin(
            @Query("user_phone") String user_phone

    );


    //////////////getting all categories///////////////
    @GET("categories.php")
    Call<Users> getCategories();


    //////////////getting all Bannner///////////////
    @GET("banners.php")
    Call<Users> getBanners();


    //////////////getting all Plates///////////////
    @GET("plates.php")
    Call<Users> getPlates();



}

