package benicio.solutions.guaponto;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import benicio.solutions.guaponto.adapter.AdapterAguaDiaria;
import benicio.solutions.guaponto.databinding.ActivityRelatorioBinding;
import benicio.solutions.guaponto.model.AguaModel;
import benicio.solutions.guaponto.model.BodyGetRotinas;
import benicio.solutions.guaponto.model.RotinaModel;
import benicio.solutions.guaponto.retrofitUtils.RetrofitUtil;
import benicio.solutions.guaponto.utils.HackUtil;
import benicio.solutions.guaponto.utils.PrefsUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelatorioActivity extends AppCompatActivity {

    private ActivityRelatorioBinding mainBinding;
    private List<RotinaModel> rotinasDiaria = new ArrayList<>();
    private List<RotinaModel> todasRotinasDiaria = new ArrayList<>();
    private AdapterAguaDiaria adapterAguaDiaria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityRelatorioBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBinding.voltar.setOnClickListener(v -> finish());

        configurarRecyclerView();

    }

    private void configurarRecyclerView() {
        mainBinding.recy.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recy.setHasFixedSize(true);
        adapterAguaDiaria = new AdapterAguaDiaria(rotinasDiaria, this);
        mainBinding.recy.setAdapter(adapterAguaDiaria);

        RetrofitUtil.createServiceApi(RetrofitUtil.createRetrofit()).getRotinas(PrefsUser.getPrefsUsers(this).getInt("id", 0)).enqueue(new Callback<BodyGetRotinas>() {
            @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
            @Override
            public void onResponse(Call<BodyGetRotinas> call, Response<BodyGetRotinas> response) {
                if (response.isSuccessful()) {

                    todasRotinasDiaria.addAll(response.body().get$values());
                    configurarGrafico();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);

                    float mediaAgua = 0.0f;
                    int divisor = 1;
                    Date ultimoRotina = null;
                    for (RotinaModel rotina : todasRotinasDiaria) {


                        try {
                            Date dataRotinaAtual = dateFormat.parse(rotina.getIngestao());

                            if ( ultimoRotina == null) {
                                ultimoRotina = dataRotinaAtual;
                            }

                            if ( !ultimoRotina.equals(dataRotinaAtual)){
                                divisor++;
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        mediaAgua += rotina.getMlIngerido();

                        if (HackUtil.isToday(rotina.getIngestao())) {
                            rotinasDiaria.add(rotina);
                        }
                    }

                    mainBinding.textMedia.setText(String.format("%.2fL", (mediaAgua / divisor) / 1000));
                    adapterAguaDiaria.notifyDataSetChanged();
                } else {
                    Toast.makeText(RelatorioActivity.this, "Problema no servidor...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BodyGetRotinas> call, Throwable throwable) {
                Toast.makeText(RelatorioActivity.this, "Problema de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarGrafico() {
        ArrayList<BarEntry> entries = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        int eixoX = 1;
        Date ultimoRotina = null;
        int somaQuantiade = 0;
        boolean acabouSoComUmDado = true;

        for (RotinaModel rotina : todasRotinasDiaria) {
            try {
                Date dataRotinaAtual = dateFormat.parse(rotina.getIngestao());

                if (ultimoRotina == null) {
                    ultimoRotina = dataRotinaAtual;
                }

                if (dataRotinaAtual.equals(ultimoRotina)) {
                    somaQuantiade += rotina.getMlIngerido();
                } else {
                    acabouSoComUmDado = false;
                    ultimoRotina = dataRotinaAtual;
                    somaQuantiade = 0;
                    somaQuantiade += rotina.getMlIngerido();
                    entries.add(new BarEntry(eixoX, (float) somaQuantiade /1000));
                    eixoX++;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

        if (acabouSoComUmDado) {
            entries.add(new BarEntry(eixoX, (float) somaQuantiade /1000));
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Quantidade de Água Ingerida em Litros");
        barDataSet.setColor(ContextCompat.getColor(this, R.color.ciano));

        BarData barData = new BarData(barDataSet);

        // Customize X-axis
        XAxis xAxis = mainBinding.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        mainBinding.chart.setData(barData);
        mainBinding.chart.setFitBars(true);

        mainBinding.chart.invalidate();
    }
}