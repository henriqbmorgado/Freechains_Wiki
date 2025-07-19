# Freechains_Wiki

Descrição: Enciclopédia cooperativa peer-to-peer (P2P) desenvolvida para ambientes não permissionados, na qual os usuários utilizam do portocolo Freechains para criam cadeias, que funcionam analogamente a tópicos individuais da enciclopédia. Cada cadeia armazena os verbetes referentes ao seu tema por meio de patches, ou seja, fragmentos de edições que compõem colaborativamente o conteúdo final do tópico. A construção dos verbetes é realizada de forma incremental. O verbete final é obtido pela aplicação sequencial dos patches, construindo o conteúdo de maneira colaborativa.

A aplicação utiliza de:

  -Freechains (https://github.com/Freechains/README/tree/master); protocolo P2P de disseminação de conteúdo.

  -diff-match-patch (https://github.com/google/diff-match-patch); biblioteca de cálculo de diff textuais e aplicação de patches.
  
Simul: Simulação do funcionamento da aplicação, mais expecificamente o funcionamento do mecanismo de consenso na rede e como ele afeta a aplicação. (https://docs.google.com/presentation/d/13-jaegHthGZFACryue2mrLdbKu6y0ODoKv_jyDxLNDE/edit?usp=sharing)

Wiki: Classe que  encapsula toda a lógica de execução de comandos do Freechains. 

  Ela oferece as funções:

  -Iniciar/parar o host Freechains.

  -Gerar e configurar chaves do usuário.

  -Entrar/Sair em cadeias (tópicos).

  -Listar cadeias (tópicos).

  -Criar posts e obter o entrada final completo de um tópico.

  -Aplicar patches de texto (com a lib diff_match_patch).

  -Enviar e receber consenso com outros peers.

  -Curtir e descurtir blocos (posts).

  -Obter blocos (post) e payloads de uma cadeia (tópico).

Main: interface com o usúario por menu textual via terminal.

