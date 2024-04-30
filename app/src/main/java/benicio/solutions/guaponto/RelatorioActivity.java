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

import java.util.ArrayList;
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
    private AdapterAguaDiaria adapterAguaDiaria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityRelatorioBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBinding.voltar.setOnClickListener(v -> finish());

        showBarChart();
        initBarChart();
        configurarRecyclerView();
    }

    private void configurarRecyclerView() {
        mainBinding.recy.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recy.setHasFixedSize(true);
//        mainBinding.recy.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapterAguaDiaria = new AdapterAguaDiaria(rotinasDiaria, this);
        mainBinding.recy.setAdapter(adapterAguaDiaria);

        RetrofitUtil.createServiceApi(RetrofitUtil.createRetrofit()).getRotinas(PrefsUser.getPrefsUsers(this).getInt("id", 0)).enqueue(new Callback<BodyGetRotinas>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<BodyGetRotinas> call, Response<BodyGetRotinas> response) {
                if (response.isSuccessful()) {
                    for (RotinaModel rotina : response.body().get$values()) {
                        Log.d("mayara", "onResponse: " + rotina.getIngestao());
                        if (HackUtil.isToday(rotina.getIngestao())) {
                            rotinasDiaria.add(rotina);
                        }
                    }
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

    private void showBarChart() {
        ArrayList<Double> valueList = new ArrayList<Double>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Title";

        //input data
        for (int i = 0; i < 6; i++) {
            valueList.add(i * 100.1);
        }

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        initBarDataSet(barDataSet);
        BarData data = new BarData(barDataSet);
        mainBinding.chart.setData(data);
        mainBinding.chart.invalidate();
    }

    private void initBarDataSet(BarDataSet barDataSet) {
        //Changing the color of the bar
        barDataSet.setColor(ContextCompat.getColor(this, R.color.ciano));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
        //change color text
        barDataSet.setValueTextColor(Color.WHITE);
    }

    private void initBarChart() {
        //hiding the grey background of the chart, default false if not set
        mainBinding.chart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        mainBinding.chart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        mainBinding.chart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        mainBinding.chart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        mainBinding.chart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        mainBinding.chart.animateX(1000);

        XAxis xAxis = mainBinding.chart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mainBinding.chart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = mainBinding.chart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = mainBinding.chart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

    }
}