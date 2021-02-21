# chat-multicast-java-andrewcs0901

Desenvolvido por Andrew Costa Silva, estudante de Engenharia de Software na PUC MG para a disciplina de Laboratório de Desenvolvimento de Aplicações Móveis e Distribuidas

## O que a aplicação se trata

- Simulação de um chat na linguagem Java que utiliza os endereços IP classe D para Multicast (224.0.0.1 até 239.255.255.255);

## Modelo Básico para solução

### Divisão de partes

#### Pacote server

No cenário onde um cliente realiza solicitações de listagem de grupos e usuários ao servidor, o servidor processa essas mensagens e envia de volta ao cliente, via protocolo UDP.

- Classe _MainServer_:

    Realiza o papel de instânciar as classes do servidor UDPServer e do DAOGoup.

- Classe: _UDPServer_:

    Realiza o papel de controle, tratamento e reenvio de respostas ao cliente.
  
- Classe: _GroupDAO_:

    Realiza o papel de gestão dos grupos.

#### Pacote client

Além disso, para o funcionamento da comunicação via multicast o cliente requisita ao servidor a criação de um grupo, e o mesmo, retorna um código para ingressar, portanto a partir desse momento o usuário pode se juntar ou não a esse grupo, e podendo ao ingressar receber mensagens a qualquer tempo de outros integrantes.

- Classe _App_:

    Realiza o papel de instânciar a classe Client e exibir o menu principal.

- Classe _Client_:

    Realiza o papel de envio e recebimento de mensagens.

#### Pacote util

Utilizada para a divisão de classes que podem ser utilizadas pelo cliente e pelo servidor.

- Classe _DataCollection_

    Realiza o papel de encapsular um objeto em lista recebido pelo cliente que foi enviado pelo servidor.

- Classe _EAcceptedOptions_

    Realiza o papel de definir quais protocolos são aceitos e os retornem em uma lista.

- Classe _Message_

    Realiza o papel de encapsular um objeto de cliente para cliente, no qual os outros usuários ao receber uma mensagem poderão saber o nome de quem a enviou.

- Classe _MultiCastIpGenerator_

    Classe estática que realiza o papel de gerar IPs no range válido para o uso multicast, note que ao chegar no endereço limite de IP será lançada uma exceção.

## Instruções para execução

  1. Realize a compilação dos arquivos .java via terminal `javac <nome do arquivo>` ou importe o projeto via IDE;

  2. (Recomendado) Para a execução simultânea dos arquivos, abra via terminal na pasta que contenham os arquivos compilados .class `java <server.MainServer>` - servidor, `java <client.App>` - cliente.

## Lista de comandos

Ao executar as classes mencionadas nas instruções do tópico anterior, abra o terminal onde o cliente -client.App esteja sendo executado e acesse a opção para ingressar no chat, e em seguida você poderá utilizar o comando `/?` dentro do terminal para visualizar a lista de comandos disponíveis, como também listada na tabela abaixo:

  Comando  | Função
------------- | -------------
/list-users  |   lista usuários
/list-groups  | listar grupos
/create  | criar grupo
/in < ip >  | entrar no grupo
/leave  | sair do grupo
/exit  | encerrar a aplicação
