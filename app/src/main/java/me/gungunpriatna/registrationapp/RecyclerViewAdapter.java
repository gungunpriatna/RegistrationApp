package me.gungunpriatna.registrationapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewNPM.setText(result.getNpm());
        holder.textViewNama.setText(result.getNama());
        holder.textViewKelas.setText(result.getKelas());
        holder.textViewSesi.setText(result.getSesi());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.textNPM) TextView textViewNPM;
        @BindView(R.id.textNama) TextView textViewNama;
        @BindView(R.id.textKelas) TextView textViewKelas;
        @BindView(R.id.textSesi) TextView textViewSesi;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String npm = textViewNPM.getText().toString();
            String nama = textViewNama.getText().toString();
            String kelas = textViewKelas.getText().toString();
            String sesi = textViewSesi.getText().toString();

            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("npm", npm);
            intent.putExtra("nama", nama);
            intent.putExtra("kelas", kelas);
            intent.putExtra("sesi", sesi);

            context.startActivity(intent);
        }
    }
}
