import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    // Definindo as constantes
    private static final String[] MESES = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private static final int MAX_SUBESTACOES = 100; // Defina um limite máximo para o número de subestações

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n === Menu ===");
            System.out.println("1 - Matriz contendo o total de energia consumido mensal para cada subestação:");
            System.out.println("2 - Subestação com maior consumo mensal:");
            System.out.println("3 - Subestação com menor consumo mensal:");
            System.out.println("4 - Total geral de consumo de energia ao longo do ano:");
            System.out.println("5 - Média de consumo mensal por subestação:");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpa o buffer

            switch (opcao) {
                case 1:
                    String arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Dados[] dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        exibirMatriz(dados);
                    }
                    break;
                case 2:
                    arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Dados[] dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        maiorConsumoPorMes(dados);
                    }
                    break;
                case 3:
                    arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Dados[] dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        menorConsumoPorMes(dados);
                    }
                    break;
                case 4:
                    arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Dados[] dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        totalGeralConsumo(dados);
                    }
                    break;
                case 5:
                    arquivoSelecionado = escolhendoArquivo();
                    if (arquivoSelecionado != null) {
                        Dados[] dados = processarCSV("/workspaces/T1_ALEST/casosdeteste/" + arquivoSelecionado);
                        mediaConsumoMensalPorSubestacao(dados);
                    }
                    break;
                case 6:
                    System.out.println("Saindo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
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

    // Definindo a estrutura para armazenar dados de consumo
    static class Dados {
        String subestacao;
        int[] consumos;  // Array com 12 valores (um para cada mês)

        Dados(String subestacao) {
            this.subestacao = subestacao;
            this.consumos = new int[12];  // Inicializa o array de consumos
        }
    }

    // Método para processar o arquivo CSV e agrupar os dados
    private static Dados[] processarCSV(String caminhoArquivo) {
        Dados[] dados = new Dados[MAX_SUBESTACOES];
        int count = 0;

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

                int mesIndex = -1;
                for (int i = 0; i < MESES.length; i++) {
                    if (MESES[i].equals(mes)) {
                        mesIndex = i;
                        break;
                    }
                }

                if (mesIndex != -1) {
                    Dados entry = findOrCreateEntry(dados, subestacao, count);
                    entry.consumos[mesIndex] += consumo;
                    if (entry.subestacao != null) {
                        count = Math.max(count, getIndex(dados, entry.subestacao) + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivo);
            e.printStackTrace();
        }

        Dados[] resultado = new Dados[count];
        for (int i = 0; i < count; i++) {
            resultado[i] = dados[i];
        }

        return resultado;
    }

    // Método para encontrar ou criar uma entrada para a subestação
    private static Dados findOrCreateEntry(Dados[] dados, String subestacao, int count) {
        int index = getIndex(dados, subestacao);
        if (index != -1) {
            return dados[index];
        }

        // Se não encontrado, cria uma nova entrada
        if (count < MAX_SUBESTACOES) {
            Dados newEntry = new Dados(subestacao);
            dados[count] = newEntry;
            return newEntry;
        } else {
            System.out.println("Número máximo de subestações atingido.");
            return null;
        }
    }

    // Método para encontrar o índice de uma subestação
    private static int getIndex(Dados[] dados, String subestacao) {
        for (int i = 0; i < dados.length; i++) {
            if (dados[i] != null && dados[i].subestacao.equals(subestacao)) {
                return i;
            }
        }
        return -1;
    }

    // Método para exibir a matriz com o formato desejado
    private static void exibirMatriz(Dados[] dados) {
        System.out.println("Matriz de Consumo por Subestação");

        // Exibe cabeçalhos dos meses
        System.out.print(String.format("%-15s", "Subestacao"));
        for (String mes : MESES) {
            System.out.print(String.format("%-10s", mes.substring(0, 3)));
        }
        System.out.println();

        // Exibe cada subestação e seus consumos
        for (Dados entry : dados) {
            if (entry != null) {
                System.out.print(String.format("%-15s", entry.subestacao));
                for (int consumo : entry.consumos) {
                    System.out.print(String.format("%-10d", consumo));
                }
                System.out.println();
            }
        }
    }

    // Método para exibir a matriz com o formato desejado e calcular o maior consumo mensal
    private static void maiorConsumoPorMes(Dados[] dados) {
        int maiorConsumo = 0;
        String subestacaoMaiorConsumo = "";
        String mesMaiorConsumo = "";

        System.out.println("Matriz de Consumo por Subestação");
        System.out.print(String.format("%-15s", "Subestacao"));
        for (String mes : MESES) {
            System.out.print(String.format("%-10s", mes.substring(0, 3)));
        }
        System.out.println();

        for (Dados entry : dados) {
            if (entry != null) {
                System.out.print(String.format("%-15s", entry.subestacao));

                for (int i = 0; i < entry.consumos.length; i++) {
                    int consumoMes = entry.consumos[i];
                    System.out.print(String.format("%-10d", consumoMes));

                    if (consumoMes > maiorConsumo) {
                        maiorConsumo = consumoMes;
                        subestacaoMaiorConsumo = entry.subestacao;
                        mesMaiorConsumo = MESES[i];
                    }
                }
                System.out.println();
            }
        }

        if (!subestacaoMaiorConsumo.isEmpty() && !mesMaiorConsumo.isEmpty()) {
            System.out.println("Subestação com maior consumo mensal:");
            System.out.println(subestacaoMaiorConsumo + " - " + mesMaiorConsumo.substring(0, 3) + " - " + maiorConsumo);
        } else {
            System.out.println("Não há dados suficientes para determinar o maior consumo mensal.");
        }
    }

    // Método para exibir a matriz com o formato desejado e calcular o menor consumo mensal
    private static void menorConsumoPorMes(Dados[] dados) {
        int menorConsumo = Integer.MAX_VALUE;
        String subestacaoMenorConsumo = "";
        String mesMenorConsumo = "";

        System.out.println("Matriz de Consumo por Subestação");
        System.out.print(String.format("%-15s", "Subestacao"));
        for (String mes : MESES) {
            System.out.print(String.format("%-10s", mes.substring(0, 3)));
        }
        System.out.println();

        for (Dados entry : dados) {
            if (entry != null) {
                System.out.print(String.format("%-15s", entry.subestacao));

                for (int i = 0; i < entry.consumos.length; i++) {
                    int consumoMes = entry.consumos[i];
                    System.out.print(String.format("%-10d", consumoMes));

                    if (consumoMes < menorConsumo && consumoMes > 0) {
                        menorConsumo = consumoMes;
                        subestacaoMenorConsumo = entry.subestacao;
                        mesMenorConsumo = MESES[i];
                    }
                }
                System.out.println();
            }
        }

        if (!subestacaoMenorConsumo.isEmpty() && !mesMenorConsumo.isEmpty()) {
            System.out.println("Subestação com menor consumo mensal:");
            System.out.println(subestacaoMenorConsumo + " - " + mesMenorConsumo.substring(0, 3) + " - " + menorConsumo);
        } else {
            System.out.println("Não há dados suficientes para determinar o menor consumo mensal.");
        }
    }

    // Método para exibir a matriz com o formato desejado e calcular o total geral de consumo de energia
    private static void totalGeralConsumo(Dados[] dados) {
        // Exibe a matriz
        exibirMatriz(dados);

        // Calcula o total geral de consumo
        int totalGeral = 0;
        for (Dados entry : dados) {
            if (entry != null) {
                for (int consumo : entry.consumos) {
                    totalGeral += consumo;
                }
            }
        }

        // Exibe o total geral
        System.out.println("Total geral de consumo: " + totalGeral);
    }

    // Método para exibir a média de consumo mensal por subestação
    private static void mediaConsumoMensalPorSubestacao(Dados[] dados) {
        System.out.println("Média de consumo mensal por subestação");

        for (Dados entry : dados) {
            if (entry != null) {
                // Calcula a média mensal
                int totalConsumo = 0;
                for (int consumo : entry.consumos) {
                    totalConsumo += consumo;
                }
                double mediaMensal = totalConsumo / 12.0;

                // Exibe o resultado formatado
                System.out.printf("%s %.2f%n", entry.subestacao, mediaMensal);
            }
        }
    }

    
}
