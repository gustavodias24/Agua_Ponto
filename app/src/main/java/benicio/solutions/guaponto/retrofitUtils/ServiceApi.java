package benicio.solutions.guaponto.retrofitUtils;

import benicio.solutions.guaponto.model.BodyGetllAllUsers;
import benicio.solutions.guaponto.model.RotinaModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {

    @GET("api/usuarios/GetAllUsuarios")
    Call<BodyGetllAllUsers> getAllUsers();

    @POST("api/rotina/PostRotina")
    Call<Void> postRotina(@Body RotinaModel rotinaModel);

}
