import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MatrizCSV {

    public static void main(String[] args) {
        String pasta = "/workspaces/T1_ALEST/casosdeteste"; // Caminho para a pasta com os arquivos CSV
        Scanner scanner = new Scanner(System.in);

        // Lista todos os arquivos CSV na pasta
        File pastaDir = new File(pasta);
        File[] arquivos = pastaDir.listFiles((dir, name) -> name.endsWith(".csv"));

        if (arquivos != null && arquivos.length > 0) {
            // Exibe a lista de arquivos CSV para o usuário
            System.out.println("Selecione um arquivo CSV para ler:");
            for (int i = 0; i < arquivos.length; i++) {
                System.out.println((i + 1) + ". " + arquivos[i].getName());
            }
            System.out.print("Digite o número do arquivo desejado: ");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            if (escolha >= 1 && escolha <= arquivos.length) {
                File arquivoSelecionado = arquivos[escolha - 1];
                System.out.println("Lendo arquivo: " + arquivoSelecionado.getName());

                // Processa o arquivo CSV e constrói a matriz
                Map<String, Map<String, Integer>> dados = processarCSV(arquivoSelecionado.getAbsolutePath());
                exibirMatriz(dados);
            } else {
                System.out.println("Escolha inválida.");
            }
        } else {
            System.out.println("Nenhum arquivo CSV encontrado na pasta.");
        }

        scanner.close();
    }

    // Método para processar o arquivo CSV e agrupar os dados
    private static Map<String, Map<String, Integer>> processarCSV(String caminhoArquivo) {
        Map<String, Map<String, Integer>> dados = new LinkedHashMap<>();
        String[] meses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        
        // Inicializa o mapa de meses
        Map<String, Integer> consumoPorMes = new LinkedHashMap<>();
        for (String mes : meses) {
            consumoPorMes.put(mes, 0);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    // Ignorar o cabeçalho
                    primeiraLinha = false;
                    continue;
                }

                // Divide a linha em colunas usando a vírgula como delimitador
                String[] colunas = linha.split(",", -1);
                String mes = colunas[0];
                String subestacao = colunas[1];
                int consumo = Integer.parseInt(colunas[2]);

                // Adiciona o consumo aos dados
                dados.putIfAbsent(subestacao, new LinkedHashMap<>(consumoPorMes));
                Map<String, Integer> consumoSubestacao = dados.get(subestacao);
                consumoSubestacao.put(mes, consumoSubestacao.get(mes) + consumo);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivo);
            e.printStackTrace();
        }

        return dados;
    }

    // Método para exibir a matriz com o formato desejado
    private static void exibirMatriz(Map<String, Map<String, Integer>> dados) {
        // Cabeçalhos dos meses
        String[] meses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        System.out.println("Matriz de Consumo por Subestação");
        System.out.print("     ");
        for (String mes : meses) {
            System.out.print(mes.substring(0, 3) + " ");
        }
        System.out.println();

        // Exibe cada subestação e seus consumos
        for (Map.Entry<String, Map<String, Integer>> entry : dados.entrySet()) {
            String subestacao = entry.getKey();
            Map<String, Integer> consumos = entry.getValue();

            System.out.print(subestacao + " ");
            for (String mes : meses) {
                System.out.print(consumos.getOrDefault(mes, 0) + " ");
            }
            System.out.println();
        }
    }
}
