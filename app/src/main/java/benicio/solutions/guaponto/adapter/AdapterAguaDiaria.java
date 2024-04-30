package benicio.solutions.guaponto.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import benicio.solutions.guaponto.R;
import benicio.solutions.guaponto.model.RotinaModel;

public class AdapterAguaDiaria extends RecyclerView.Adapter<AdapterAguaDiaria.MyViewHolder> {

    List<RotinaModel> rotinas;
    Activity c;

    public AdapterAguaDiaria(List<RotinaModel> rotinas, Activity c) {
        this.rotinas = rotinas;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rotina_layout, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.qtdIngerida.setText(rotinas.get(position).getMlIngerido() + "ml");

        LocalDateTime dateTime = LocalDateTime.parse(rotinas.get(position).getIngestao().split("\\.")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        String timeString = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        holder.horaIngerida.setText(
                timeString
        );
    }

    @Override
    public int getItemCount() {
        return rotinas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView qtdIngerida, horaIngerida;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            qtdIngerida = itemView.findViewById(R.id.qtdMlText);
            horaIngerida = itemView.findViewById(R.id.horaengeridatext);
        }
    }
}
