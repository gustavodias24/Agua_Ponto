package benicio.solutions.guaponto.retrofitUtils;

import benicio.solutions.guaponto.model.BodyGetRotinas;
import benicio.solutions.guaponto.model.BodyGetllAllUsers;
import benicio.solutions.guaponto.model.RotinaModel;
import benicio.solutions.guaponto.model.UsuarioModel;
import benicio.solutions.guaponto.model.UsuarioModelToBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceApi {

    @GET("api/usuarios/GetAllUsuarios")
    Call<BodyGetllAllUsers> getAllUsers();

    @POST("api/rotina/PostRotina")
    Call<Void> postRotina(@Body RotinaModel rotinaModel);


    @GET("api/rotina/GetRotinaByUsuarioId/{id}")
    Call<BodyGetRotinas> getRotinas(@Path("id") int id);

    @GET("api/usuarios/GetUsuario/{id}")
    Call<UsuarioModel> getUser(@Path("id") int id);

    @POST("api/usuarios/PostUsuario")
    Call<UsuarioModel> postUsuario(@Body UsuarioModelToBody usuarioModel);

    @PUT("/api/usuarios/UpdateUsuario/{id}")
    Call<Void> updateUsuario(@Body UsuarioModelToBody usuarioModel, @Path("id") int id);

}
