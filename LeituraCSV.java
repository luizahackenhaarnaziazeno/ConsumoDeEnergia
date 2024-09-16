import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class LeituraCSV {
    public static void main(String[] args) {
        String caminhoArquivoCSV = "caminho_do_arquivo.csv"; // Defina o caminho do arquivo CSV
        
        try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivoCSV))) {
            String[] linha;
            while ((linha = reader.readNext()) != null) {
                // Processa cada linha
                for (String coluna : linha) {
                    System.out.print(coluna + " ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
