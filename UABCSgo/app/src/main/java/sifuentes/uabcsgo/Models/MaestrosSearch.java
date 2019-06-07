package sifuentes.uabcsgo.Models;

import java.util.List;

import sifuentes.uabcsgo.Models.Maestros;

public class MaestrosSearch {
    List<Maestros> getSearch;

    public MaestrosSearch(List<Maestros> getSearch) {
        this.getSearch = getSearch;
    }

    public List<Maestros> getGetSearch() {
        return getSearch;
    }

    public void setGetSearch(List<Maestros> getSearch) {
        this.getSearch = getSearch;
    }
}
