package sifuentes.uabcsgo.Models;

import java.util.List;

public class Buildings {
    int id;
    String code;
    String name;
    String image;
    String description;
    String longitude;
    String latitude;
    String schedule;
    List <installments> installments;

    public Buildings(int id, String code, String name, String image, String description, String longitude, String latitude,
                     String schedule, List<sifuentes.uabcsgo.Models.installments> installments) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.image = image;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.schedule = schedule;
        this.installments = installments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public List<sifuentes.uabcsgo.Models.installments> getInstallments() {
        return installments;
    }

    public void setInstallments(List<sifuentes.uabcsgo.Models.installments> installments) {
        this.installments = installments;
    }
}