import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LeituraCSV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menu de Leitura de CSV ===");
            System.out.println("1. Ler arquivo CSV consumos 20");
            System.out.println("2. Ler arquivo CSV consumos 100");
            System.out.println("3. Ler arquivo CSV  consumos 500");
            System.out.println("4. Ler arquivo CSV  consumos 1000");
            System.out.println("5. Ler arquivo CSV  consumos 2000");
            System.out.println("6. Ler arquivo CSV  consumos 4000");
            System.out.println("7. Ler arquivo CSV  consumos 8000");
            System.out.println("8. Ler arquivo CSV  consumos 16000");
            System.out.println("9. Ler arquivo CSV  consumos 32000");
            System.out.println("10. Ler arquivo CSV  consumos 50000");
            System.out.println("11. Ler arquivo CSV  consumos 100000");
            System.out.println("12. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                lerCSV("consumos_20.csv");
                break;
                case 2:
                lerCSV("consumos_100.csv");
                break;
                case 3:
                lerCSV("consumos_500.csv");
                break;
                case 4:
                lerCSV("consumos_1000.csv");
                break;
                case 5:
                lerCSV("consumos_2000.csv");
                break;
                case 6:
                lerCSV("consumos_4000.csv");
                break;
                case 7:
                lerCSV("consumos_8000.csv");
                break;
                case 8:
                lerCSV("consumos_16000.csv");
                break;
                case 9:
                lerCSV("consumos_3200.csv");
                break;
                case 10:
                lerCSV("consumos_50000.csv");
                break;
                case 11:
                lerCSV("consumos_10000.csv");
                break;
                case 12:
                    System.out.println("Saindo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    // Método para ler e exibir o conteúdo de um CSV
    public static void lerCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int linhaNumero = 1;

            // Lê linha por linha do arquivo CSV
            while ((linha = br.readLine()) != null) {
                System.out.print("Linha " + linhaNumero + ": ");
                
                // Divide a linha em colunas usando a vírgula como delimitador
                String[] colunas = linha.split(",");

                // Exibe o conteúdo de cada coluna
                for (String coluna : colunas) {
                    System.out.print(coluna + " ");
                }
                
                System.out.println();  // Quebra de linha após cada linha do CSV
                linhaNumero++;  // Incrementa o número da linha
            }
            System.out.println("--- Fim do arquivo ---");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivo);
            e.printStackTrace();
        }
    }
}

