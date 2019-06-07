package sifuentes.uabcsgo.Models;

public class installments {
    int id;
    String code;
    String name;
    String building_id;

    public installments(int id, String code, String name, String building_id) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.building_id = building_id;
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

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }
}
