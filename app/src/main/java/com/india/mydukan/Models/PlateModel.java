package com.india.mydukan.Models;

public class PlateModel {
    public PlateModel(){
        ////////empty constructer ///////
    }

   private  String plateImg,plate_id;

    public PlateModel(String plateImg, String plate_id) {
        this.plateImg = plateImg;
        this.plate_id = plate_id;
    }

    public String getPlateImg() {
        return plateImg;
    }

    public void setPlateImg(String plateImg) {
        this.plateImg = plateImg;
    }

    public String getPlate_id() {
        return plate_id;
    }

    public void setPlate_id(String plate_id) {
        this.plate_id = plate_id;
    }
}

