# Freechains_Wiki
Utilização: java Main (aplicação); java Simul (simulação de uso por multiplos usúarios); (obs: a aplicação deve estar no mesmo diretório que o Freechains para funcionar corretamente).

Descrição: Enciclopédia cooperativa peer-to-peer (P2P) desenvolvida para ambientes não permissionados, na qual os usuários utilizam do protocolo Freechains para criam cadeias, que funcionam analogamente a tópicos individuais da enciclopédia.
Cada cadeia armazena o verbetes referentes ao seu tema por meio de patches, ou seja,  post com fragmentos de edições que compõem colaborativamente o conteúdo final do tópico. 
A construção dos verbetes é realizada de forma incremental. O verbete final é obtido pela aplicação sequencial (determinada pela função de consensus do Freechains) dos patches, construindo o conteúdo de maneira colaborativa.

A aplicação utiliza de:

  -Freechains (https://github.com/Freechains/README/tree/master); protocolo P2P de disseminação de conteúdo.

  -diff-match-patch (https://github.com/google/diff-match-patch); biblioteca de cálculo de diff textuais e aplicação de patches.
  
Main: interface com o usúario por menu textual via terminal.

Wiki: Classe que  encapsula toda a lógica de execução de comandos do Freechains. 

  Ela oferece as funções:

  -Iniciar/Parar o host Freechains.

  -Gerar chaves do usuário.

  -Entrar/Sair em cadeias (tópicos).

  -Listar cadeias (tópicos).

  -Criar posts e obter o entrada final completo de um tópico.

  -Obter consenso (lista de blocos da cadeia visiveis e ordenados) e Heads blocked (lista de blocos bloqueados da cadeia) .

  -Enviar e receber os blocos (posts) da cadeia (tópico) com outros peers.

  -Curtir e descurtir blocos (posts).
  
  -Obter reps de blocos (posts) e usuarios de uma cadeia (tópico).

  -Obter blocos (post) e payloads (patches) de uma cadeia (tópico).

  Limitações:
  
  -A aplicação não aceita arquivos como entrada para o conteudo de um bloco, apenas aceita texto inserido direto pelo terminal.

  -A aplicação não implementa as funções do Freechains: freechains chains listen; freechains chain name genesis; freechains peer addr:port chains.

Simul: Simulação do funcionamento da aplicação, mais expecificamente o funcionamento do mecanismo de consenso na rede e como ele afeta a aplicação. (https://docs.google.com/presentation/d/13-jaegHthGZFACryue2mrLdbKu6y0ODoKv_jyDxLNDE/edit?usp=sharing)




