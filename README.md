# Programa para leitura de CSV

## Sobre
A problemática solicitava que fosse realizada a leitura do arquivo CSV enviado
e após a leitura retorna-se: 

Os eventos agrupados por dispositivo e ordenados 
cronológicamente, os mais antigos primeiro.

Como saída: `{codigoDispositivo};{distanciaDoLatLongInformado};{dataHoraDoEventoEmISO8601};{payload}`

## Instruções

Para compilar o programa basta executar o maven:
`mvn clean install`

Copiar o arquivo `eventlog.csv` na raiz de onde o jar for executado e depois rodar:
 
Foram escolhidos dois jeitos de implementar a aplicação um deles usando a lib [OpenCSV](http://opencsv.sourceforge.net/)
e outro usando só bibliotecas nativas do java.

Para rodar a aplicação escolha o modo substituindo a variavel `{tipoDeImplementação}` por:
`--native` ou `--opencsv`
 
`java -jar target/desafio/desafio.jar {tipoDeImplementação} --location {latitude} {longitude}`

### Opnião sobre a solução:
Fiz uma separação das camadas de serviço e modelos, implementei o pattern Strategy na execução do
programa. Para fazer a aplicação crescer ficou bem facil, só implementar uma nova `CSVService`.
Não gostei da velocidade de aplicação pensando no caso real informado, a performance média foi de:

> OpenCSV 2.154sec até 2.164sec

> Nativo 2.144sec até 2.154sec

Com um arquivo de 1 milhão de linhas.

### Próximos passos:
O que eu faria se tivesse mais tempo seria implementar um DB em memória ou leitura do arquivo em 
Batch.
