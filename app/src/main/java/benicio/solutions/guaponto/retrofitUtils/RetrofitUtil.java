package benicio.solutions.guaponto.retrofitUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static Retrofit createRetrofit(){
        return new Retrofit.Builder().baseUrl("https://apiaguaponto2024.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServiceApi createServiceApi(Retrofit retrofit){
        return retrofit.create(ServiceApi.class);
    }
}
