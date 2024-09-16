import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class LeituraCSV {
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
                lerCSV(arquivoSelecionado.getAbsolutePath()); // Lê o arquivo CSV
            } else {
                System.out.println("Escolha inválida.");
            }
        } else {
            System.out.println("Nenhum arquivo CSV encontrado na pasta.");
        }

        scanner.close();
    }

    // Método para ler e organizar o conteúdo de um CSV
    public static void lerCSV(String caminhoArquivo) {
        Map<String, List<String[]>> dadosPorSubestacao = new HashMap<>();

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

                if (colunas.length >= 3) {
                    String subestacao = colunas[1]; // Segunda coluna: Subestacao
                    String[] dados = new String[]{colunas[0], colunas[2]}; // Mes, Consumo(kWh)

                    // Adiciona os dados ao mapa
                    dadosPorSubestacao.putIfAbsent(subestacao, new ArrayList<>());
                    dadosPorSubestacao.get(subestacao).add(dados);
                }
            }

            // Exibe o nome do arquivo lido
            System.out.println("Arquivo processado: " + caminhoArquivo);

            // Exibe os dados organizados por subestação
            for (Map.Entry<String, List<String[]>> entry : dadosPorSubestacao.entrySet()) {
                String subestacao = entry.getKey();
                List<String[]> dados = entry.getValue();

                System.out.println("Dados para " + subestacao + ":");
                for (String[] linhaArray : dados) {
                    System.out.println("Mês: " + linhaArray[0] + ", Consumo(kWh): " + linhaArray[1]);
                }
                System.out.println(); // Linha em branco entre os dados das subestações
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivo);
            e.printStackTrace();
        }
    }
}
