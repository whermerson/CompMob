package br.com.softdicas.compmob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CadastroCliente extends AppCompatActivity {

    Button btnCadastrarCliente;
    Button btnFoto;
    EditText identificacaoClienteReferencia;
    EditText qtdElementosReferencia;
    EditText consumoPerCaptaReferencia;
    ImageView imageFoto;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;


    String identificacaoCliente;
    int qtdElementos;
    int consumoPerCapta;
    String tipoDeElemento = "";
    private Uri minhaUri;

    String uid;
    String profileUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnCadastrarCliente = findViewById(R.id.buttonCadastrarCliente);
        btnFoto = findViewById(R.id.buttonFoto);
        identificacaoClienteReferencia = findViewById(R.id.identificacaoCliente);
        qtdElementosReferencia = findViewById(R.id.qtdElementos);
        consumoPerCaptaReferencia = findViewById(R.id.consumoPerCapta);
        imageFoto = findViewById(R.id.imageFoto);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        tipoDeElemento = extras.getString("tipoDeElemento");


        switch (tipoDeElemento) {
            case "Apartamento": {
                qtdElementosReferencia.setHint("Quantidade de Dormitórios");
                break;
            }
            case "Residência Popular": {
                qtdElementosReferencia.setHint("Quantidade de Moradores");
                break;
            }
            case "Escola": {
                qtdElementosReferencia.setHint("Quantidade de Alunos");
                break;
            }
            case "Mercado": {
                qtdElementosReferencia.setHint("Quantidade de m² de Área");
                break;
            }
            case "Restaurante": {
                qtdElementosReferencia.setHint("Quantidade de Refeições");
                break;
            }

        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarFoto();
            }
        });



        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!identificacaoClienteReferencia.getText().toString().isEmpty() && !qtdElementosReferencia.getText().toString().isEmpty() && !consumoPerCaptaReferencia.getText().toString().isEmpty() && !tipoDeElemento.equals("")) {
                    identificacaoCliente = identificacaoClienteReferencia.getText().toString();
                    qtdElementos = Integer.parseInt(qtdElementosReferencia.getText().toString());
                    consumoPerCapta = Integer.parseInt(consumoPerCaptaReferencia.getText().toString());
                    insertNewRegistry();
                    Intent intent = new Intent(CadastroCliente.this, ListaDeClientes.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(CadastroCliente.this, "É necessário preencher os dados ou escolher uma nova categoria", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            minhaUri = data.getData();
            Bitmap bitmap = null;
            if(minhaUri!=null){
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), minhaUri);
                    imageFoto.setImageBitmap(bitmap);
                    btnFoto.setAlpha(0);
                    imageFoto.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void selecionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    public void insertNewRegistry(){
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(minhaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        profileUrl = uri.toString();

                        uid = mAuth.getUid();

                        Map<String, Object> cliente = new HashMap<>();
                        cliente.put("identificacaoCliente", identificacaoCliente);
                        cliente.put("qtdElementos", qtdElementos);
                        cliente.put("consumoPerCapta", consumoPerCapta);
                        cliente.put("tipoDeElemento", tipoDeElemento);
                        cliente.put("uid", uid);
                        cliente.put("profileUrl", profileUrl);

                        // Add a new document with a generated ID
                        db.collection("clientes")
                                .add(cliente)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error adding document", e);
                                    }
                                });

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(CadastroCliente.this, MainActivity.class);
            startActivity(intent);
        }
    }

}
