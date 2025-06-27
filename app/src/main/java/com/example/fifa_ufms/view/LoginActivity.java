package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.entities.User;
import com.example.fifa_ufms.utils.PasswordHasher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        btnLogin.setOnClickListener(v -> loginUser());

        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Busca o usuário no banco de dados em uma thread separada
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            User user = CampeonatoDatabase.getInstance(getApplicationContext()).userDao().getUserByEmail(email);

            String passwordHash = PasswordHasher.hashPassword(password);

            runOnUiThread(() -> {
                if (user != null && user.getPasswordHash().equals(passwordHash)) {
                    // Login bem-sucedido!
                    Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                    // Abrir a MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    // Fechar a tela de Login para que o usuário não possa voltar
                    finish();
                } else {
                    // Caso as credenciais estejam inválidas
                    Toast.makeText(LoginActivity.this, "E-mail ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
