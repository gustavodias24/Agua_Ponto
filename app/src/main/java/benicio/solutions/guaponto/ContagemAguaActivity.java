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