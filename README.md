<div align="center">

## Consumo De energia
- Seu grupo acaba de ser contratado por uma empresa de energia para desenvolver uma aplicação que mostre o consumo
de energia por subestação ao longo do ano.

- O cálculo dos resultados é feito a partir dos dados lidos em um arquivo CSV com o registro dos consumos (em kWh) em
um ano para diferentes subestações.

- Existe apenas 05 subestações, cada uma com um nome específico. Os nomes são Planalto, Aurora, Litoral, Horizonte e
Progresso.

- O arquivo CSV contendo as informações possui o seguinte formato:
Mes,Subestação,Consumo(kWh)

1. 01 Dezembro,Aurora,350
2. 02 Fevereiro,Litoral,784
3. 03 Marco,Horizonte,631
4. 04 Marco,Horizonte,174
5. 05 Julho,Planalto,59
6. 06 Agosto,Progresso,1025
7. 07 Novembro,Planalto,1030
8. 08 Outubro,Aurora,788
9. 09 Setembro,Litoral,544
10.  10 Fevereiro,Horizonte,859

-  A primeira coluna contém Mês do consumo, a segunda o nome da subestação e a terceira o total do consumo em kWh.
Podem ocorrer vários registros de consumo para a mesma subestação no mesmo mês, conforme aparece nas linhas 03
e 04.
- Inclusive o valor do consumo pode ser igual, então você não deve descartar nenhuma linha lida.
- A partir desse arquivo o programa deve gerar as informações mencionadas anteriormente.
- Segue abaixo os exemplos gerados para o arquivo de exemplo:
# Matriz de Consumo por Subestação

A tabela abaixo apresenta o consumo mensal de energia por subestação.

| Subestação   | Jan | Fev | Mar | Abr | Mai | Jun | Jul | Ago | Set | Out | Nov | Dez |
|--------------|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|
| Planalto     | 0   | 0   | 0   | 0   | 0   | 0   | 59  | 0   | 0   | 0   | 1030| 0   |
| Aurora       | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 788 | 0   | 350 |
| Litoral      | 0   | 784 | 0   | 0   | 0   | 0   | 0   | 0   | 544 | 0   | 0   | 0   |
| Horizonte    | 0   | 859 | 805 | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Progresso    | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 1025| 0   | 0   | 0   | 0   |


- ## Subestação com maior consumo mensal:
- ## Planalto - Nov

- ## Subestação com menor consumo mensal
- ## Planalto - Jul

- ## Total geral de consumo
- ## 6244


# :hammer: Funcionalidades do projeto
O programa realiza os seguintes funções:
O programa deve ler o arquivo e após o processamento apresentar as seguintes informações:
- Matriz contendo o total de energia consumido mensal para cada subestação, conforme exemplo fornecido.
- A subestação com maior consumo mensal e em qual mês isso ocorreu.
- A subestação com menor consumo mensal e em qual mês isso ocorreu.
- O total geral de consumo de energia ao longo do ano.
- Média de consumo mensal por subestação
- Uma lista dos meses e total de energia consumida ordenada por consumo de energia em ordem decrescente.
  
## Observações e limitações da solução:
- Existem apenas cinco subestações.
- O programa deve gerar um arquivo com nome resultados.txt contento as informações solicitadas. Não é necessário
mostrar em tela.
- Estão disponibilizados diferentes casos de testes com dados para serem testados.
- Implementar em JAVA. Não pode ser usada nenhuma biblioteca adicional (java.util.Arrays ou ArrayList por exemplo)

  ## Linguagem Utilizada:
<div style="display: inline_block"><br>
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" /> 

# Autora:

| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/142232479?v=4" width=115><br><sub>Luiza Hackenhaar Naziazeno</sub>](https://github.com/luizahackenhaarnaziazeno)|
| :---: |

