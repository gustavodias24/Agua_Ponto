package benicio.solutions.guaponto;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import benicio.solutions.guaponto.databinding.ActivityCasdastroBinding;
import benicio.solutions.guaponto.model.UsuarioModel;
import benicio.solutions.guaponto.model.UsuarioModelToBody;
import benicio.solutions.guaponto.retrofitUtils.RetrofitUtil;
import benicio.solutions.guaponto.utils.PrefsUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasdastroActivity extends AppCompatActivity {

    private ActivityCasdastroBinding mainBinding;
    private UsuarioModelToBody usuarioModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityCasdastroBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        usuarioModel = new UsuarioModelToBody();


        mainBinding.cadastrar.setOnClickListener(v -> {

            boolean prosseguir = true;

            String nome = mainBinding.NomeField.getEditText().getText().toString();
            String email = mainBinding.emailField.getEditText().getText().toString();
            String senha = mainBinding.senhaField.getEditText().getText().toString();

            String nascimento = mainBinding.nascimentoField.getEditText().getText().toString();
            String horaAcorda = mainBinding.horaAcordaField.getEditText().getText().toString();
            String horaDorme = mainBinding.horaDormeField.getEditText().getText().toString();


            usuarioModel.setDataNascimento(nascimento);
            usuarioModel.setRotinaAcorda(horaAcorda);
            usuarioModel.setRotinaDorme(horaDorme);

            int pesoInt = 0;
            try {
                String peso = mainBinding.pesoField.getEditText().getText().toString();
                pesoInt = Integer.parseInt(peso);
            } catch (Exception ignored) {
            }

            double alturaDouble = 0;
            try{
                String altura = mainBinding.alturaField.getEditText().getText().toString();
                alturaDouble = Double.parseDouble(altura.trim().replace(",", "."));
            }catch (Exception ignored){}


            usuarioModel.setAltura(alturaDouble);

            usuarioModel.setNome(nome);
            usuarioModel.setEmail(email);
            usuarioModel.setSenha(senha);
            usuarioModel.setPeso(pesoInt);

            if (prosseguir) {
                Toast.makeText(this, "Cadastrando...", Toast.LENGTH_SHORT).show();
                RetrofitUtil.createServiceApi(
                        RetrofitUtil.createRetrofit()
                ).postUsuario(usuarioModel).enqueue(new Callback<UsuarioModel>() {
                    @Override
                    public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                        if (response.isSuccessful()) {
                            finish();
                            PrefsUser.getEditorUsers(CasdastroActivity.this).putInt("id", response.body().getId()).apply();
                            startActivity(new Intent(CasdastroActivity.this, ContagemAguaActivity.class));
                        } else {
                            Log.d("mayara", "onResponse: " + response.code());
                            Log.d("mayara", "onResponse: " + response.message());
                            Toast.makeText(CasdastroActivity.this, "Problema de conexão!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioModel> call, Throwable throwable) {
                        Toast.makeText(CasdastroActivity.this, "Problema de conexão!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Date parseDateTime(String horaDoUsuario) {
        LocalDateTime dataAtual = LocalDateTime.now();
        String dataFormatada = dataAtual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dataHoraFormatada = dataFormatada + "T" + horaDoUsuario + ".0000000";

        LocalDateTime localDateTime = LocalDateTime.parse(dataHoraFormatada, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"));
        return java.sql.Timestamp.valueOf(String.valueOf(localDateTime));
    }

}