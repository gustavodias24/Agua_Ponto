package benicio.solutions.guaponto;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import benicio.solutions.guaponto.databinding.ActivityInstrucoesBinding;
import benicio.solutions.guaponto.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBinding.novoCadastro.setOnClickListener( v -> startActivity(new Intent(this, CasdastroActivity.class)));
        mainBinding.esqueceuSenha.setOnClickListener( v -> startActivity(new Intent(this, TrocarSenhaActivity.class)));

        mainBinding.entrar.setOnClickListener( v -> {
            startActivity(new Intent(this, ContagemAguaActivity.class));
        });
    }
}