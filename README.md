# chat-multicast-java-andrewcs0901

## O que a aplicação se trata

- Simulação de um chat na linguagem Java que utiliza os endereços IP classe D para Multicast (224.0.0.1 até 239.255.255.255);

## Modelo Básico para solução

### Divisão de partes

- Dividiu-se sistema em duas partes, o cliente realiza solicitações de listagem de grupos e usuários ao servidor, o servidor processa essas mensagens e envia de volta ao cliente, via protocolo UDP.

  - Classe _DatagramSocket_.

- Além disso, para o funcionamento da comunicação via multicast o cliente requisita ao servidor a criação de um grupo, e o mesmo retorna um código para ingressar, a partir desse momento o usuário se junta a esse grupo podendo receber mensagens a qualquer tempo de outros integrantes.

  - Classe _MulticastSocket_.

## Instruções para execução

  1. Realize a compilação dos arquivos .java via terminal `javac <nome do arquivo>` ou importe o arquivo via IDE;

  2. (Recomendado) Para a execução simultânea do arquivos, abra via terminal na pasta que contenham os arquivos compilados .class `java <server.MainServer>` - servidor, `java <client.App>` - cliente.

## Lista de comandos

  Comando  | Função
------------- | -------------
/list-users  |   lista usuários
/list-groups  | listar grupos
/create  | criar grupo
/in < ip >  | entrar no grupo
/leave  | sair do grupo
/exit  | encerrar a aplicação
