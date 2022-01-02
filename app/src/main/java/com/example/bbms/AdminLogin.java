package com.example.bbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    Button btnAdminLogin;
    EditText etAdminUsername, etAdminPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        etAdminUsername = findViewById(R.id.etAdminUsername);
        etAdminPassword = findViewById(R.id.etAdminPassword);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = etAdminUsername.getText().toString();
                String pw = etAdminPassword.getText().toString();

                if (un.equals("admin") && pw.equals("admin")) {
                    Intent a = new Intent(AdminLogin.this, AdminLoggedInMenu.class);
                    startActivity(a);
//                    Toast.makeText(AdminLogin.this, "Done", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(AdminLogin.this, "invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}