package benicio.solutions.guaponto.retrofitUtils;

import benicio.solutions.guaponto.model.BodyGetllAllUsers;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {

    @GET("api/usuarios/GetAllUsuarios")
    Call<BodyGetllAllUsers> getAllUsers();

}
