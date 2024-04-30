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
import benicio.solutions.guaponto.retrofitUtils.RetrofitUtil;
import benicio.solutions.guaponto.utils.PrefsUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasdastroActivity extends AppCompatActivity {

    private ActivityCasdastroBinding mainBinding;
    private UsuarioModel usuarioModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityCasdastroBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        usuarioModel = new UsuarioModel();


        mainBinding.cadastrar.setOnClickListener(v -> {

            boolean prosseguir = true;

            String nome = mainBinding.NomeField.getEditText().getText().toString();
            String email = mainBinding.emailField.getEditText().getText().toString();
            String senha = mainBinding.senhaField.getEditText().getText().toString();
            usuarioModel.setDataNascimento("2024-04-29T12:42:44.1311389");
            usuarioModel.setRotinaAcorda("2024-04-29T12:42:44.1311389");
            usuarioModel.setRotinaDorme("2024-04-29T12:42:44.1311389");
//            try {
//                String nascimento = formatDate(parseDate(mainBinding.nascimentoField.getEditText().getText().toString()));
//                String horaAcorda = formatDate(parseDateTime(mainBinding.horaAcordaField.getEditText().getText().toString()));
//                String horaDorme = formatDate(parseDateTime(mainBinding.horaDormeField.getEditText().getText().toString()));


//
//            } catch (Exception e) {
//                Toast.makeText(this, "Data/Hora inválido", Toast.LENGTH_SHORT).show();
//                prosseguir = false;
//            }

            int pesoInt = 0;
            try {
                String peso = mainBinding.pesoField.getEditText().getText().toString();
                pesoInt = Integer.parseInt(peso);
            } catch (Exception ignored) {
            }
//            String altura = mainBinding.alturaField.getEditText().getText().toString();


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
                            Log.d("mayara", "onResponse: " + usuarioModel.getDataNascimento());
                            Log.d("mayara", "onResponse: " + usuarioModel.getRotinaAcorda());
                            Log.d("mayara", "onResponse: " + usuarioModel.getRotinaDorme());
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

    private Date parseDate(String dateString) throws ParseException {
        // Define o formato da data inserida pelo usuário
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Converte a string para um objeto Date
        try {
            return inputFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatDate(Date date) {
        // Define o formato desejado para a data
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        // Formata a data conforme o formato desejado
        return outputFormat.format(date);
    }
}