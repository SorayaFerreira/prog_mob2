package com.example.fifa_ufms.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.entities.User;
import com.example.fifa_ufms.utils.PasswordHasher;

import com.example.fifa_ufms.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etName, etEmail, etPassword;
    private ImageView ivProfilePhoto;
    private Button btnRegister;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 2. INICIALIZAÇÃO DOS COMPONENTES (findViewById)
        // Conecta as variáveis Java com os componentes do XML.
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);

        ivProfilePhoto.setOnClickListener(v -> openGallery());
        btnRegister.setOnClickListener(v -> registerUser());
    }

    // abre a galeria do celular
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Se a imagem foi escolhida com sucesso, guardamos o URI dela
            photoUri = data.getData();
            ivProfilePhoto.setImageURI(photoUri);
        }
    }

    // cadastro do usuário
    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Valida se foi preenchido nome
        if (name.isEmpty()) {
            etName.setError("O nome é obrigatório!");
            etName.requestFocus();
            return;
        }
        // valida se foi preenchido email
        if (email.isEmpty()) {
            etEmail.setError("O e-mail é obrigatório!");
            etEmail.requestFocus();
            return;
        }
        // valida se foi preenchida a senha
        if (password.isEmpty()) {
            etPassword.setError("A senha é obrigatória1!");
            etPassword.requestFocus();
            return;
        }

        if (photoUri == null) {
            Toast.makeText(this, "Por favor, selecione uma foto.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Realiza a criptografia da senha com hash
        String passwordHash = PasswordHasher.hashPassword(password);

        // Criando objeto User
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordHash); // Salva o hash da senha, não a senha em si
        newUser.setPhotoUri(photoUri.toString()); // Salva o endereço da foto como string

        // Usa uma thread separada para não travar a tela ao salvar usuário no banco de dados
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            CampeonatoDatabase.getInstance(getApplicationContext()).userDao().insert(newUser);

            // Volta para a thread principal
            runOnUiThread(() -> {
                Toast.makeText(RegisterActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                finish(); // Fecha a tela de cadastro e volta para a tela de Login
            });
        });
    }
}