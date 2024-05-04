package benicio.solutions.guaponto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import benicio.solutions.guaponto.databinding.ActivityLoginBinding;
import benicio.solutions.guaponto.databinding.ActivityTrocarSenhaBinding;
import benicio.solutions.guaponto.model.BodyGetllAllUsers;
import benicio.solutions.guaponto.model.UsuarioModel;
import benicio.solutions.guaponto.model.UsuarioModelToBody;
import benicio.solutions.guaponto.retrofitUtils.RetrofitUtil;
import benicio.solutions.guaponto.utils.PrefsUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrocarSenhaActivity extends AppCompatActivity {

    private ActivityTrocarSenhaBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityTrocarSenhaBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mainBinding.entrar.setOnClickListener(v -> {

            Toast.makeText(this, "Aguarde...", Toast.LENGTH_SHORT).show();
            String email = mainBinding.emailField.getEditText().getText().toString().trim();
            String senha = mainBinding.novaSenhaField.getEditText().getText().toString().trim();
            String confirmarSenha = mainBinding.confirmarNovaSenhaField.getEditText().getText().toString().trim();

            if (senha.equals(confirmarSenha)) {
                RetrofitUtil.createServiceApi(
                        RetrofitUtil.createRetrofit()
                ).getAllUsers().enqueue(new Callback<BodyGetllAllUsers>() {
                    @Override
                    public void onResponse(Call<BodyGetllAllUsers> call, Response<BodyGetllAllUsers> response) {
                        if (response.isSuccessful()) {
                            BodyGetllAllUsers users = response.body();

                            boolean encontrado = false;
                            for (UsuarioModel user : users.get$values()) {
                                if (user.getEmail().equals(email)) {
                                    encontrado = true;
                                    UsuarioModelToBody usuarioModelToBody = new UsuarioModelToBody(
                                            user.getEmail(),
                                            senha,
                                            user.getNome(),
                                            user.getSobrenome(),
                                            user.getDataNascimento(),
                                            user.getRotinaAcorda(),
                                            user.getRotinaDorme(),
                                            user.getPeso(),
                                            user.getAltura(),
                                            user.getIdade(),
                                            user.getMediaAgua()
                                    );


                                    RetrofitUtil.createServiceApi(
                                            RetrofitUtil.createRetrofit()
                                    ).updateUsuario(usuarioModelToBody, user.getId()).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(TrocarSenhaActivity.this, "Senha atualizada!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(TrocarSenhaActivity.this, "Problema de servidor.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable throwable) {
                                            Toast.makeText(TrocarSenhaActivity.this, "Problema de Conexão.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }


                            if (!encontrado) {
                                Toast.makeText(TrocarSenhaActivity.this, "Email não encontrado", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(TrocarSenhaActivity.this, "Problema de Conexão.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BodyGetllAllUsers> call, Throwable throwable) {
                        Toast.makeText(TrocarSenhaActivity.this, "Erro de Conexão.", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(this, "As senhas precisam ser iguais", Toast.LENGTH_SHORT).show();
            }


        });
    }
}