
# Divisor de Arquivos em partes

Este é um programa Java que divide um arquivo de texto em várias partes. O programa usa uma caixa de diálogo de seleção de arquivo para permitir que o usuário selecione o arquivo de entrada e, em seguida, solicita ao usuário o número de partes nas quais dividir o arquivo. O programa então grava cada parte do arquivo em um arquivo de saída separado, com a primeira linha do arquivo de entrada repetida no início de cada parte.

O programa usa um leitor em buffer para ler o arquivo de entrada linha por linha e um gravador em buffer para gravar cada parte do arquivo em um arquivo de saída separado. O programa primeiro lê a primeira linha do arquivo de entrada e a armazena em uma variável. Em seguida, ele conta o número total de linhas no arquivo de entrada e calcula o número de linhas em cada parte do arquivo de saída. Em seguida, itera sobre cada parte do arquivo de saída, gravando a primeira linha do arquivo de entrada no início de cada parte, seguida pelo número apropriado de linhas do arquivo de entrada. Por fim, ele grava a última linha do arquivo de entrada em cada parte do arquivo de saída, exceto a última parte.

O programa também inclui um método auxiliar que conta o número total de linhas em um arquivo, que é usado para calcular o número de linhas em cada parte do arquivo de saída.

Observe que este programa assume que o arquivo de entrada tem pelo menos uma linha e que o usuário insere um número inteiro positivo para o número de partes nas quais dividir o arquivo. Ele também ignora quaisquer erros que possam ocorrer durante a leitura ou gravação de arquivos.
