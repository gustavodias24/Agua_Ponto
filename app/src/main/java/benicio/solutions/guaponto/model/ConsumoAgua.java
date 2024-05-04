package benicio.solutions.guaponto.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumoAgua {
    int quantidadeDeAguaDoDia;
    int dia;

    public ConsumoAgua(int quantidadeDeAguaDoDia, int dia) {
        this.quantidadeDeAguaDoDia = quantidadeDeAguaDoDia;
        this.dia = dia;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<ConsumoAgua> calcularConsumoAgua(List<Map<String, Object>> ingestoes) {
        Map<LocalDate, Integer> consumoPorDia = new HashMap<>();

        // Calcula o consumo de água para cada dia
        for (Map<String, Object> ingestao : ingestoes) {
            String data = (String) ingestao.get("ingestao");
            int mlIngerido = (int) ingestao.get("mlIngerido");
            LocalDate dia = LocalDate.parse(data.substring(0, 10)); // Extrai a data

            consumoPorDia.put(dia, consumoPorDia.getOrDefault(dia, 0) + mlIngerido);
        }

        // Converte o mapa para a lista de ConsumoAgua
        List<ConsumoAgua> resultado = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : consumoPorDia.entrySet()) {
            resultado.add(new ConsumoAgua(entry.getValue(), entry.getKey().getDayOfMonth()));
        }

        return resultado;
    }

    public int getQuantidadeDeAguaDoDia() {
        return quantidadeDeAguaDoDia;
    }

    public void setQuantidadeDeAguaDoDia(int quantidadeDeAguaDoDia) {
        this.quantidadeDeAguaDoDia = quantidadeDeAguaDoDia;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
