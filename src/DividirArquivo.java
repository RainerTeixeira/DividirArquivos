import java.awt.Dimension;
import java.io.*;
import javax.swing.*;
/**
Esta classe implementa a funcionalidade de dividir um arquivo em múltiplas
partes. O usuário deve selecionar o arquivo original e o diretório de
destino, e especificar o número de partes desejado.
@author Rainer Teixeira
*/

public class DividirArquivo extends JFrame {

    public static void main(String[] args) {
        // Solicita ao usuário que selecione o arquivo original
        File arquivoOriginal = selecionarArquivo("Selecione o arquivo original");
        if (arquivoOriginal == null) {
            System.exit(0);
        }

        // Solicita ao usuário que selecione o diretório de destino
        File diretorioDestino = selecionarDiretorio("Selecione o diretório de destino");
        if (diretorioDestino == null) {
            System.exit(0);
        }

        // Solicita ao usuário que informe o número de partes desejado
        int numPartes = obterNumeroDePartes();

        // Divide o arquivo original em várias partes
        dividirArquivo(arquivoOriginal, diretorioDestino, numPartes);
    }

    // Método que exibe uma janela de seleção de arquivo
    private static File selecionarArquivo(String titulo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);
        fileChooser.setPreferredSize(new Dimension(1000, 550));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    // Método que exibe uma janela de seleção de diretório
    private static File selecionarDiretorio(String titulo) {
        JFileChooser dirChooser = new JFileChooser();
        dirChooser.setDialogTitle(titulo);
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = dirChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return dirChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    // Método que solicita ao usuário o número de partes desejado
    private static int obterNumeroDePartes() {
        String input = JOptionPane.showInputDialog("Quantas partes deseja dividir o arquivo?");
        return Integer.parseInt(input);
    }

// Método que divide o arquivo original em várias partes
private static void dividirArquivo(File arquivoOriginal, File diretorioDestino, int numPartes) {
    try {
        String primeiraLinha = lerPrimeiraLinha(arquivoOriginal);
        String ultimaLinha = lerUltimaLinha(arquivoOriginal);
        int numLinhas = contarLinhas(arquivoOriginal);
        int linhasPorParte = numLinhas / numPartes;
        int linhasRestantes = numLinhas % numPartes;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoOriginal))) {
            br.readLine(); // Ignora a primeira linha
            for (int i = 1; i <= numPartes; i++) {
                String nomeArquivo = arquivoOriginal.getName().substring(0, arquivoOriginal.getName().lastIndexOf(".")) + ".part" + i + arquivoOriginal.getName().substring(arquivoOriginal.getName().lastIndexOf("."));
                File arquivoDestino = new File(diretorioDestino, nomeArquivo);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDestino))) {
                    escreverConteudoDeParte(br, bw, primeiraLinha, ultimaLinha, linhasPorParte, linhasRestantes, i, numPartes);
                }
            }
            JOptionPane.showMessageDialog(null, "Arquivo dividido com sucesso!");
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao dividir o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    private static String lerPrimeiraLinha(File arquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            return br.readLine();
        }
    }

    private static String lerUltimaLinha(File arquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String ultimaLinha = null;
            String linha;
            while ((linha = br.readLine()) != null) {
                ultimaLinha = linha;
            }
            return ultimaLinha;
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

    private static void escreverConteudoDeParte(BufferedReader br, BufferedWriter bw, String primeiraLinha, String ultimaLinha,
                                                int linhasPorParte, int linhasRestantes, int parteAtual, int numPartes) throws IOException {
        bw.write(primeiraLinha);
        bw.newLine();

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

        if (parteAtual == numPartes && linhasRestantes > 0) {
            for (int j = 0; j < linhasRestantes; j++) {
                String linha = br.readLine();
                if (linha == null) {
                    break;
                }
                bw.write(linha);
                bw.newLine();
            }
        }

        if (parteAtual < numPartes && ultimaLinha != null) {
            bw.write(ultimaLinha);
            bw.newLine();
        }
    }
}


