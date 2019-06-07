package sifuentes.uabcsgo.Interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sifuentes.uabcsgo.Models.Horario;
import sifuentes.uabcsgo.Models.MaestrosSearch;
import sifuentes.uabcsgo.Models.Buildings;
import sifuentes.uabcsgo.Models.Notas;
import sifuentes.uabcsgo.Models.Usuario;

public interface routesInterface {
    @GET("/api/notesuser/{id}")
    Call <List<Notas>> getNotes(@Path("id") int id);

    @GET("/api/schedules/{id}")
    Call <List<Horario>> getHorario(@Path("id") int id);

    @FormUrlEncoded
    @POST("/api/notes")
    Call <Notas> postNotes(@Field("title") String title,
                           @Field("description") String description, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("/api/login")
    Call <Usuario> login(@Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/logout")
    Call <Usuario> logout(@Field("i") int i);

    @FormUrlEncoded
    @POST("/api/users")
    Call <Usuario> register(@Field("name") String name,@Field("email") String email,
                            @Field("password") String password, @Field("shift") String shift,
                            @Field("semester_id") int semester_id, @Field("degree_id") int degree_id);

    @FormUrlEncoded
    @POST("/api/putnotes/{id}")
    Call <Notas> putNote(@Path("id") int id, @Field("title") String title,
                         @Field("description") String description);

    @GET("/api/deletenotes/{id}")
    Call <Notas> deleteNote(@Path("id") int id);

    @GET("/api/buildings")
    Call <List<Buildings>> getBuildings();


    @GET("/api/teachers")
    Call <List<MaestrosSearch>> getTeachers();

}