package br.com.softdicas.compmob;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private ArrayList<ClienteClass> dataset;

    public MyAdapter(ArrayList<ClienteClass> myDataset) {
        dataset = myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dados_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.nomeCliente.setText(dataset.get(position).getidentificacaoCliente());

        Picasso.get().load(dataset.get(position).getprofileUrl()).into(holder.imgView);

        holder.myConstraintRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.myConstraintRow.getContext(), ExibeCliente.class);
                intent.putExtra("identificacaoCliente", dataset.get(position).getidentificacaoCliente());
                intent.putExtra("qtdElementos", dataset.get(position).getqtdElementos());
                intent.putExtra("consumoPerCapta", dataset.get(position).getconsumoPerCapta());
                intent.putExtra("tipoDeElemento", dataset.get(position).gettipoDeElemento());
                intent.putExtra("uid", dataset.get(position).getuid());
                intent.putExtra("profileUrl", dataset.get(position).getprofileUrl());

                holder.myConstraintRow.getContext().startActivity(intent);
            }
        });

        holder.myConstraintRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(holder.myConstraintRow.getContext(), dataset.get(position).getidentificacaoCliente(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout myConstraintRow;
        TextView nomeCliente;
        ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myConstraintRow = itemView.findViewById(R.id.linearlayoutClickable);
            nomeCliente = itemView.findViewById(R.id.textViewNomeCliente);
            imgView = itemView.findViewById(R.id.camera);


        }
    }


}
