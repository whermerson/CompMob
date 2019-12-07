package br.com.softdicas.compmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EscolheCategoria extends AppCompatActivity {

    Button buttonResidenciaPopular;
    Button buttonApartamento;
    Button buttonEscola;
    Button buttonMercado;
    Button buttonRestaurante;

    String tipoDeElemento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhe_categoria);

        buttonResidenciaPopular = findViewById(R.id.buttonResidenciaPopular);
        buttonApartamento = findViewById(R.id.buttonApartamento);
        buttonEscola = findViewById(R.id.buttonEscola);
        buttonMercado = findViewById(R.id.buttonMercado);
        buttonRestaurante = findViewById(R.id.buttonRestaurante);

    }

    public void onButtonClick(View view) {
        switch (view.getId()) {

            case R.id.buttonResidenciaPopular: {
                tipoDeElemento = "Residência Popular";
                break; }
            case R.id.buttonApartamento: {
                tipoDeElemento = "Apartamento";
                break; }
            case R.id.buttonEscola: {
                tipoDeElemento = "Escola";
                break; }
            case R.id.buttonMercado: {
                tipoDeElemento = "Mercado";
                break; }
            case R.id.buttonRestaurante: {
                tipoDeElemento = "Restaurante";
                break; }

        }


        Intent intent = new Intent(EscolheCategoria.this, CadastroCliente.class);
        intent.putExtra("tipoDeElemento", tipoDeElemento);
        startActivity(intent);
    }

}
