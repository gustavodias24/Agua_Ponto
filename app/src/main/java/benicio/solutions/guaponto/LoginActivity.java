package benicio.solutions.guaponto;

import android.content.Intent;
import android.content.SharedPreferences;
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

import benicio.solutions.guaponto.databinding.ActivityInstrucoesBinding;
import benicio.solutions.guaponto.databinding.ActivityLoginBinding;
import benicio.solutions.guaponto.model.BodyGetllAllUsers;
import benicio.solutions.guaponto.model.UsuarioModel;
import benicio.solutions.guaponto.retrofitUtils.RetrofitUtil;
import benicio.solutions.guaponto.utils.HackUtil;
import benicio.solutions.guaponto.utils.PrefsUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBinding.novoCadastro.setOnClickListener(v -> startActivity(new Intent(this, CasdastroActivity.class)));
        mainBinding.esqueceuSenha.setOnClickListener(v -> startActivity(new Intent(this, TrocarSenhaActivity.class)));

        mainBinding.entrar.setOnClickListener(v -> {

            Toast.makeText(this, "Logando...", Toast.LENGTH_SHORT).show();

            String email, senha, senhaNormal;

            email = mainBinding.emailField.getEditText().getText().toString().trim();
            senhaNormal = mainBinding.senhaField.getEditText().getText().toString().trim();
            senha = HackUtil.gerarHash(mainBinding.senhaField.getEditText().getText().toString().trim());

            RetrofitUtil.createServiceApi(
                    RetrofitUtil.createRetrofit()
            ).getAllUsers().enqueue(new Callback<BodyGetllAllUsers>() {
                @Override
                public void onResponse(Call<BodyGetllAllUsers> call, Response<BodyGetllAllUsers> response) {
                    if (response.isSuccessful()) {
                        BodyGetllAllUsers users = response.body();

                        boolean encontrado = false;
                        for (UsuarioModel user : users.get$values()) {
                            if( user.getEmail().equals(email) && (user.getSenha().equals(senha) || user.getSenha().equals(senhaNormal)) ){
                                finish();
                                PrefsUser.getEditorUsers(LoginActivity.this).putInt("id", user.getId()).apply();
                                encontrado = true;
                                startActivity(new Intent(LoginActivity.this, ContagemAguaActivity.class));
                            }
                        }


                        if ( !encontrado){
                            Toast.makeText(LoginActivity.this, "Email ou Senha inválido.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "Problema de Conexão.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BodyGetllAllUsers> call, Throwable throwable) {
                    Toast.makeText(LoginActivity.this, "Erro de Conexão.", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}