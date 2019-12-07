package br.com.softdicas.compmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class CadastroUsuario extends AppCompatActivity {

    EditText email;
    EditText password;
    Button buttonCadastrar;
    FirebaseAuth auth;
    FirebaseUser user;

    String emailString;
    String passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        email = findViewById(R.id.editTextEmail2);
        password = findViewById(R.id.editTextPassword);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        if (user == null){
            buttonCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailString = email.getText().toString();
                    passwordString = password.getText().toString();

                    if (emailString.isEmpty() || passwordString.isEmpty()) {
                        Toast.makeText(CadastroUsuario.this, "É necessário email e senha", Toast.LENGTH_SHORT).show();
                    } else {

                        auth.createUserWithEmailAndPassword(emailString, passwordString)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(CadastroUsuario.this, "Cadastrado!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(CadastroUsuario.this, ListaDeClientes.class);

                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                        startActivity(intent);


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CadastroUsuario.this, "Não cadastrado! Tente novamente mais tarde" + emailString + passwordString, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });
        }
    }
}
