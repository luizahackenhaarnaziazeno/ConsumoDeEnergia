import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Criartxt {

    // Definindo as constantes
    private static final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    public static void main(String[] args) {
        String caminhoArquivo = "resultados.txt";
        String relatorio = gerarRelatorio();

        try (FileWriter writer = new FileWriter(new File(caminhoArquivo))) {
            writer.write(relatorio);
            System.out.println("Relatório criado com sucesso em " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo de relatório: " + e.getMessage());
        }
    }

    private static String gerarRelatorio() {
        // Gerar dados fictícios para o exemplo
        Dados[] dados = gerarDadosFicticios();
        
        StringBuilder sb = new StringBuilder();

        // Adicionar matriz de consumo
        sb.append("Matriz de Consumo por Subestação\n");
        sb.append(exibirMatriz(dados));
        sb.append("\n");

        // Adicionar maior consumo mensal
        sb.append("Subestação com maior consumo mensal\n");
        sb.append(maiorConsumoPorMes(dados));
        sb.append("\n");

        // Adicionar menor consumo mensal
        sb.append("Subestação com menor consumo mensal\n");
        sb.append(menorConsumoPorMes(dados));
        sb.append("\n");

        // Adicionar total geral de consumo
        sb.append("Total geral de consumo de energia ao longo do ano\n");
        sb.append(totalGeralConsumo(dados));
        sb.append("\n");

        // Adicionar média de consumo mensal por subestação
        sb.append("Média de consumo mensal por subestação\n");
        sb.append(mediaConsumoMensalPorSubestacao(dados));
        sb.append("\n");

        // Adicionar lista de consumo total mensal ordenada
        sb.append("Lista do consumo total mensal ordenada\n");
        sb.append(listaConsumoTotalMensalOrdenada(dados));
        sb.append("\n");

        return sb.toString();
    }

    private static Dados[] gerarDadosFicticios() {
        // Dados fictícios para o exemplo
        Dados[] dados = new Dados[3];
        dados[0] = new Dados("Planalto");
        dados[1] = new Dados("Aurora");
        dados[2] = new Dados("Litoral");

        // Preencher dados fictícios
        for (int i = 0; i < dados.length; i++) {
            for (int j = 0; j < 12; j++) {
                dados[i].consumos[j] = (i + 1) * 100 + j * 10; // Dados fictícios
            }
        }
        return dados;
    }

    private static String exibirMatriz(Dados[] dados) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s", "Subestacao"));
        for (String mes : MESES) {
            sb.append(String.format("%-10s", mes.substring(0, 3)));
        }
        sb.append("\n");

        for (Dados entry : dados) {
            sb.append(String.format("%-15s", entry.subestacao));
            for (int consumo : entry.consumos) {
                sb.append(String.format("%-10d", consumo));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String maiorConsumoPorMes(Dados[] dados) {
        int maiorConsumo = 0;
        String subestacaoMaiorConsumo = "";
        String mesMaiorConsumo = "";

        StringBuilder sb = new StringBuilder();
        for (Dados entry : dados) {
            for (int i = 0; i < entry.consumos.length; i++) {
                int consumoMes = entry.consumos[i];
                if (consumoMes > maiorConsumo) {
                    maiorConsumo = consumoMes;
                    subestacaoMaiorConsumo = entry.subestacao;
                    mesMaiorConsumo = MESES[i];
                }
            }
        }

        if (!subestacaoMaiorConsumo.isEmpty() && !mesMaiorConsumo.isEmpty()) {
            sb.append(subestacaoMaiorConsumo).append(" - ").append(mesMaiorConsumo.substring(0, 3)).append(" - ").append(maiorConsumo);
        } else {
            sb.append("Não há dados suficientes para determinar o maior consumo mensal.");
        }

        return sb.toString();
    }

    private static String menorConsumoPorMes(Dados[] dados) {
        int menorConsumo = Integer.MAX_VALUE;
        String subestacaoMenorConsumo = "";
        String mesMenorConsumo = "";

        StringBuilder sb = new StringBuilder();
        for (Dados entry : dados) {
            for (int i = 0; i < entry.consumos.length; i++) {
                int consumoMes = entry.consumos[i];
                if (consumoMes < menorConsumo && consumoMes > 0) {
                    menorConsumo = consumoMes;
                    subestacaoMenorConsumo = entry.subestacao;
                    mesMenorConsumo = MESES[i];
                }
            }
        }

        if (!subestacaoMenorConsumo.isEmpty() && !mesMenorConsumo.isEmpty()) {
            sb.append(subestacaoMenorConsumo).append(" - ").append(mesMenorConsumo.substring(0, 3)).append(" - ").append(menorConsumo);
        } else {
            sb.append("Não há dados suficientes para determinar o menor consumo mensal.");
        }

        return sb.toString();
    }

    private static String totalGeralConsumo(Dados[] dados) {
        int totalGeral = 0;
        for (Dados entry : dados) {
            for (int consumo : entry.consumos) {
                totalGeral += consumo;
            }
        }

        return "Total geral de consumo: " + totalGeral;
    }

    private static String mediaConsumoMensalPorSubestacao(Dados[] dados) {
        StringBuilder sb = new StringBuilder();
        for (Dados entry : dados) {
            int totalConsumo = 0;
            for (int consumo : entry.consumos) {
                totalConsumo += consumo;
            }
            double mediaMensal = totalConsumo / 12.0;

            sb.append(String.format("%s %.2f%n", entry.subestacao, mediaMensal));
        }
        return sb.toString();
    }

    private static String listaConsumoTotalMensalOrdenada(Dados[] dados) {
        int[] consumosMensais = new int[12];
        for (Dados entry : dados) {
            for (int i = 0; i < 12; i++) {
                consumosMensais[i] += entry.consumos[i];
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-15s%n", "Mês", "Consumo total"));

        // Ordenar os meses por consumo em ordem decrescente
        for (int i = 0; i < consumosMensais.length; i++) {
            for (int j = i + 1; j < consumosMensais.length; j++) {
                if (consumosMensais[j] > consumosMensais[i]) {
                    // Troca
                    int temp = consumosMensais[i];
                    consumosMensais[i] = consumosMensais[j];
                    consumosMensais[j] = temp;
                    String tempMes = MESES[i];
                    MESES[i] = MESES[j];
                    MESES[j] = tempMes;
                }
            }
        }

        for (int i = 0; i < consumosMensais.length; i++) {
            sb.append(String.format("%-15s %-15d%n", MESES[i].substring(0, 3), consumosMensais[i]));
        }
        return sb.toString();
    }

    // Classe interna para Dados
    static class Dados {
        String subestacao;
        int[] consumos;  // Array com 12 valores (um para cada mês)

        Dados(String subestacao) {
            this.subestacao = subestacao;
            this.consumos = new int[12];  // Inicializa o array de consumos
        }
    }
}
