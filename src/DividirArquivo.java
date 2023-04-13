import java.awt.Dimension;
import java.io.*;
import javax.swing.*;

public class DividirArquivo {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(1000, 550)); // Definindo as dimensões da janela do fileChooser
        fileChooser.showOpenDialog(null);

        File arquivoOriginal = fileChooser.getSelectedFile();

        JFileChooser dirChooser = new JFileChooser();
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dirChooser.showSaveDialog(null);

        File diretorioDestino = dirChooser.getSelectedFile();

        // Armazenando a primeira e a última linha do arquivo original
        String primeiraLinha = null;
        String ultimaLinha = null;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoOriginal))) {
            primeiraLinha = br.readLine(); // Lê a primeira linha
            String linha;
            while ((linha = br.readLine()) != null) {
                ultimaLinha = linha;
            }
        } catch (IOException e) {
            return;
        }

        int numPartes = Integer.parseInt(JOptionPane.showInputDialog("Quantas partes deseja dividir o arquivo?"));

        int numLinhas = contarLinhas(arquivoOriginal);
        int linhasPorParte = numLinhas / numPartes;
        int linhasRestantes = numLinhas % numPartes;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoOriginal))) {
            br.readLine(); // Ignora a primeira linha
            for (int i = 1; i <= numPartes; i++) {
                String nomeArquivo = arquivoOriginal.getName() + ".part" + i;
                File arquivoDestino = new File(diretorioDestino, nomeArquivo);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDestino))) {

                    // Escrevendo a primeira linha em todos os arquivos de parte
                    bw.write(primeiraLinha);
                    bw.newLine();

                    // Escrevendo as linhas da parte atual, exceto a primeira linha (já escrita acima)
                    int linhasEscritas = 0;
                    while (linhasEscritas < linhasPorParte) {
                        String linha = br.readLine();
                        if (linha == null) {
                            break;
                        }
                        bw.write(linha);
                        bw.newLine();
                        linhasEscritas++;
                    }

                    // Se ainda há linhas restantes e estamos na última parte, escrevê-las aqui
                    if (i == numPartes && linhasRestantes > 0) {
                        for (int j = 0; j < linhasRestantes; j++) {
                            String linha = br.readLine();
                            if (linha == null) {
                                break;
                            }
                            bw.write(linha);
                            bw.newLine();
                        }
                    }

                    // Escrevendo a última linha no final de cada arquivo de parte, exceto na última parte
                    if (i < numPartes && ultimaLinha != null) {
                        bw.write(ultimaLinha);
                        bw.newLine();
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Arquivo dividido com sucesso!");
        } catch (IOException e) {
        }
    }

    private static int contarLinhas(File arquivo) {
        int numLinhas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            while (br.readLine() != null) {
                numLinhas++;
            }
        } catch (IOException e) {
            return -1;
        }
        return numLinhas;
    }
}
