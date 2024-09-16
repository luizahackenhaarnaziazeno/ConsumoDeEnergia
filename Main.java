import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        
        Scanner scannerp = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n === Menu de leitura de CSV ===");
            System.out.println("1 - Matriz contendo o total de energia consumido mensal para cada subestação:");
            System.out.println("2 - Subestação com maior consumo mensal:");
            System.out.println("3 - Subestação com menor consumo mensal:");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scannerp.nextInt();
            scannerp.nextLine();  // Limpa o buffer

            switch (opcao) {
                case 1:
                    String arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Map<String, Map<String, Integer>> dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        exibirMatriz(dados);
                    }
                    break;
                case 2:
                    arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Map<String, Map<String, Integer>> dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        MaiorConsumoPorMes(dados);
                    }
                    break;
                case 3:
                    arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Map<String, Map<String, Integer>> dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        MenorConsumoPorMes(dados);
                    }
                    break;
                case 4:
                    System.out.println("Saindo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scannerp.close();
    }

    // Método para escolher o arquivo
    private static String escolhendoArquivo() {
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
            
            int escolha;
            while (true) {
                try {
                    escolha = Integer.parseInt(scanner.nextLine()); // Lê e valida a escolha do usuário
                    if (escolha >= 1 && escolha <= arquivos.length) {
                        break;
                    } else {
                        System.out.println("Número inválido. Tente novamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, insira um número.");
                }
            }

            File arquivoSelecionado = arquivos[escolha - 1];
            System.out.println("Arquivo selecionado: " + arquivoSelecionado.getName());

            return arquivoSelecionado.getName(); // Retorna o nome do arquivo selecionado
        } else {
            System.out.println("Nenhum arquivo CSV encontrado na pasta.");
            return null; // Retorna null se não houver arquivos na pasta
        }
    }
    
    // Método para processar o arquivo CSV e agrupar os dados
    private static Map<String, Map<String, Integer>> processarCSV(String caminhoArquivo) {
        Map<String, Map<String, Integer>> dados = new LinkedHashMap<>();
        String[] meses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

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
                dados.putIfAbsent(subestacao, new LinkedHashMap<>());
                Map<String, Integer> consumoSubestacao = dados.get(subestacao);
                consumoSubestacao.put(mes, consumoSubestacao.getOrDefault(mes, 0) + consumo);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivo);
            e.printStackTrace();
        }

        return dados;
    }

    // Método para exibir a matriz com o formato desejado
    // Método para exibir a matriz com o formato desejado
    private static void exibirMatriz(Map<String, Map<String, Integer>> dados) {
        // Cabeçalhos dos meses
        String[] meses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        
        // Define o comprimento fixo para cada coluna (ajuste conforme necessário)
        int larguraColuna = 10;
        int larguraSubestacao = 15;  // Maior largura para a coluna da subestação
    
        System.out.println("Matriz de Consumo por Subestação");
        
        // Exibe cabeçalhos dos meses
        System.out.print(String.format("%-" + larguraSubestacao + "s", "Subestacao"));
        for (String mes : meses) {
            System.out.print(String.format("%-" + larguraColuna + "s", mes.substring(0, 3)));
        }
        System.out.println();
    
        // Exibe cada subestação e seus consumos
        for (Map.Entry<String, Map<String, Integer>> entry : dados.entrySet()) {
            String subestacao = entry.getKey();
            Map<String, Integer> consumos = entry.getValue();
    
            System.out.print(String.format("%-" + larguraSubestacao + "s", subestacao));
            for (String mes : meses) {
                System.out.print(String.format("%-" + larguraColuna + "d", consumos.getOrDefault(mes, 0)));
            }
            System.out.println();
        }
    }
    
    

    // Método para exibir a matriz com o formato desejado e calcular o maior consumo mensal
    private static void MaiorConsumoPorMes(Map<String, Map<String, Integer>> dados) {
        // Cabeçalhos dos meses
        String[] meses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        // Variáveis para armazenar o maior consumo e a subestação/mês correspondentes
        int maiorConsumo = 0;
        String subestacaoMaiorConsumo = "";
        String mesMaiorConsumo = "";

        // Exibe a matriz de consumo
        System.out.println("Matriz de Consumo por Subestação");
        int larguraColuna = 10;  // A mesma largura para alinhar com o cabeçalho
        System.out.print(String.format("%-" + larguraColuna + "s", "Subestacao"));
        for (String mes : meses) {
            System.out.print(String.format("%-" + larguraColuna + "s", mes.substring(0, 3)));
        }
        System.out.println();

        // Exibe cada subestação e seus consumos, e calcula o maior consumo geral
        for (Map.Entry<String, Map<String, Integer>> entry : dados.entrySet()) {
            String subestacao = entry.getKey();
            Map<String, Integer> consumos = entry.getValue();

            System.out.print(String.format("%-" + larguraColuna + "s", subestacao));
            
            for (String mes : meses) {
                int consumoMes = consumos.getOrDefault(mes, 0);
                System.out.print(String.format("%-" + larguraColuna + "d", consumoMes));

                // Verifica se o consumo deste mês é o maior até agora
                if (consumoMes > maiorConsumo) {
                    maiorConsumo = consumoMes;
                    subestacaoMaiorConsumo = subestacao;
                    mesMaiorConsumo = mes;
                }
            }
            System.out.println();
        }

        // Exibe a subestação com maior consumo e o mês correspondente
        if (!subestacaoMaiorConsumo.isEmpty() && !mesMaiorConsumo.isEmpty()) {
            System.out.println("Subestação com maior consumo mensal:");
            System.out.println(subestacaoMaiorConsumo + " - " + mesMaiorConsumo.substring(0, 3) + " - " + maiorConsumo);
        } else {
            System.out.println("Não há consumos válidos para determinar o maior consumo.");
        }
    }

    // Método para exibir a matriz com o formato desejado e calcular o menor consumo mensal
    private static void MenorConsumoPorMes(Map<String, Map<String, Integer>> dados) {
        // Cabeçalhos dos meses
        String[] meses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        // Variáveis para armazenar o menor consumo e a subestação/mês correspondentes
        int menorConsumo = Integer.MAX_VALUE;
        String subestacaoMenorConsumo = "";
        String mesMenorConsumo = "";

        // Exibe a matriz de consumo
        System.out.println("Matriz de Consumo por Subestação");
        int larguraColuna = 10;  // A mesma largura para alinhar com o cabeçalho
        System.out.print(String.format("%-" + larguraColuna + "s", "Subestacao"));
        for (String mes : meses) {
            System.out.print(String.format("%-" + larguraColuna + "s", mes.substring(0, 3)));
        }
        System.out.println();

        // Exibe cada subestação e seus consumos, e calcula o menor consumo geral
        for (Map.Entry<String, Map<String, Integer>> entry : dados.entrySet()) {
            String subestacao = entry.getKey();
            Map<String, Integer> consumos = entry.getValue();

            System.out.print(String.format("%-" + larguraColuna + "s", subestacao));
            
            for (String mes : meses) {
                int consumoMes = consumos.getOrDefault(mes, 0);
                System.out.print(String.format("%-" + larguraColuna + "d", consumoMes));

                // Verifica se o consumo deste mês é o menor até agora
                if (consumoMes > 0 && consumoMes < menorConsumo) {
                    menorConsumo = consumoMes;
                    subestacaoMenorConsumo = subestacao;
                    mesMenorConsumo = mes;
                }
            }
            System.out.println();
        }

        // Exibe a subestação com menor consumo e o mês correspondente
        if (!subestacaoMenorConsumo.isEmpty() && !mesMenorConsumo.isEmpty()) {
            System.out.println("Subestação com menor consumo mensal:");
            System.out.println(subestacaoMenorConsumo + " - " + mesMenorConsumo.substring(0, 3) + " - " + menorConsumo);
        } else {
            System.out.println("Não há consumos válidos para determinar o menor consumo.");
        }
    }
}
