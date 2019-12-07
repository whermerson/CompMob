package br.com.softdicas.compmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ExibeCliente extends AppCompatActivity {

    TextView idtCliente;
    TextView qtdElementos;
    TextView consumoEstimado;
    TextView hidrometroRecomendado;
    TextView categoriaCliente;
    ImageView imageFotoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_cliente);

        idtCliente = findViewById(R.id.idtCliente);
        qtdElementos = findViewById(R.id.qtdElementos);
        consumoEstimado = findViewById(R.id.consumoEstimado);
        hidrometroRecomendado = findViewById(R.id.hidrometro);
        categoriaCliente = findViewById(R.id.categoriaCliente);
        imageFotoCliente = findViewById(R.id.imageFotoCliente);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String tmp = extras.getString("identificacaoCliente");
        int tmp2 = extras.getInt("qtdElementos");
        int tmp3 = extras.getInt("consumoPerCapta");
        String tmp4 = extras.getString("tipoDeElemento");
        String tmp5 = extras.getString("uid");
        String tmp6 = extras.getString("profileUrl");

        int consumoEstimadoTmp;
        double consumoEstimadoVar;

        consumoEstimadoTmp = tmp2*tmp3;

        consumoEstimadoVar = consumoEstimadoTmp*0.03;

        DecimalFormat df = new DecimalFormat("0.00");

        String hidrometro;

        if(consumoEstimadoVar < 90){
            hidrometro = "Hidrômetro com Qmax(1,5 m³/h)";
        } else if(consumoEstimadoVar < 165) {
            hidrometro = "Hidrômetro com Qmax(3 m³/h)";
        } else if(consumoEstimadoVar < 420) {
            hidrometro = "Hidrômetro com Qmax(7/10 m³/h)";
        } else if(consumoEstimadoVar < 1200) {
            hidrometro = "Hidrômetro com Qmax(20 m³/h)";
        } else {
            hidrometro = "Fora de Escala, tente novamente!";
        }

        consumoEstimado.setText(df.format(consumoEstimadoVar) + "m³/Mês");
        idtCliente.setText(tmp);
        qtdElementos.setText("Quantidade de Moradores: "+tmp2);
        hidrometroRecomendado.setText(hidrometro);
        categoriaCliente.setText(tmp4);
        Picasso.get().load(tmp6).into(imageFotoCliente);
        imageFotoCliente.setVisibility(View.VISIBLE);

    }
}
