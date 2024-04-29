package benicio.solutions.guaponto;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import benicio.solutions.guaponto.databinding.ActivityContagemAguaBinding;
import benicio.solutions.guaponto.databinding.ActivityLoginBinding;
import benicio.solutions.guaponto.databinding.DefinirAguaIngeridaLayoutBinding;
import benicio.solutions.guaponto.model.RotinaModel;
import benicio.solutions.guaponto.retrofitUtils.RetrofitUtil;
import benicio.solutions.guaponto.utils.PrefsUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContagemAguaActivity extends AppCompatActivity {

    private ActivityContagemAguaBinding mainBinding;

    private Dialog dialogAddAgua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityContagemAguaBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBinding.adicionarAgu.setOnClickListener(v -> dialogAddAgua.show());

        configurarDialogAgua();
    }

    private void configurarDialogAgua() {
        AlertDialog.Builder b = new AlertDialog.Builder(ContagemAguaActivity.this);

        DefinirAguaIngeridaLayoutBinding viewDialog = DefinirAguaIngeridaLayoutBinding.inflate(getLayoutInflater());

        viewDialog.confirmar.setOnClickListener(v -> {

            RotinaModel rotinaModel = new RotinaModel();

            Toast.makeText(this, "Cadastrando...", Toast.LENGTH_SHORT).show();

            int mlIngerida = 0;

            try {
                mlIngerida = Integer.parseInt(viewDialog.mlField.getEditText().getText().toString());
            } catch (Exception ignored) {
            }

            if (mlIngerida == 0) {
                Toast.makeText(this, "Insira um valor válido!", Toast.LENGTH_SHORT).show();
            } else {
                rotinaModel.setMlIngerido(mlIngerida);
                rotinaModel.setUsuarioId(PrefsUser.getPrefsUsers(ContagemAguaActivity.this).getInt("id", 0));

                RetrofitUtil.createServiceApi(
                        RetrofitUtil.createRetrofit()
                ).postRotina(rotinaModel).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            dialogAddAgua.dismiss();
                            viewDialog.mlField.getEditText().setText("");
                        } else {
                            Toast.makeText(ContagemAguaActivity.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(ContagemAguaActivity.this, "Problema de conexão.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


//        rotinaModel.setMlIngerido();
        b.setView(viewDialog.getRoot());
        dialogAddAgua = b.create();
    }

    @SuppressLint("NonConstantResourceId")
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.Relatorio) {
                startActivity(new Intent(this, RelatorioActivity.class));
                return true;
            }
            return false;
        });

        popupMenu.show();
    }


}