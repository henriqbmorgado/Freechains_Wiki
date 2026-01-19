public class Simul {

  public static void Sync(Wiki[] wikis, String topic) {
    for (int j = 0; j < wikis.length; j++) {
      for (int i = 0; i < wikis.length; i++) {
        if (j != i) {
          wikis[j].sendConsensus(topic, "localhost:" + wikis[i].wiki_port);
        }
      }
    }
  }

  public static void main(String[] args) {

    Wiki[] wikis = new Wiki[4];

    String topic = "#Ciclo_da_Agua";
    Integer base_port = 8330;

    for (int i = 0; i < wikis.length; i++) {
      Integer port = base_port + i;
      wikis[i] = new Wiki(port.toString());
      String dir = "/tmp/simulation/user" + i;
      String pass = "pass" + i;

      wikis[i].startHost(dir);
      wikis[i].setUserKeys(pass);
      wikis[i].enterChain(topic, wikis[0].user_pubkey);
    }
    System.out.println("");

    // Pioneiro (User0) cria a primeira entrada para o tópico
    String entry0 = "o ciclo da agua descreve como a agua se move pela terra inclui fases de evaporacao, condensacao e precipitacao";
    String post0 = wikis[0].createEntry(topic, entry0);
    System.out.println("hash post0: " + post0 + "\n");

    Sync(wikis, topic);
    System.out.println("");

    // User1 verifica a entrada e decide modificala
    System.out.println("chain entry: " + wikis[1].getEntry(topic) + "\n");
    String entry1 = "O ciclo da agua descreve como a agua se move pela Terra. Inclui fases de evaporacao, condensacao e precipitacao.";
    String post1 = wikis[1].createEntry(topic, entry1);
    System.out.println("hash post1: " + post1 + "\n");

    Sync(wikis, topic);
    System.out.println("");

    // Pioneiro (User0) da like em no post do User1
    wikis[0].likePost(post1, topic);
    System.out.println("");

    Sync(wikis, topic);
    System.out.println("");

    System.out.println("chain consensus: " + wikis[0].getBlocks(topic) + "\n");

    // Ambos User2 e User3 ao mesmo tempo verificam a entrada e decidem modificala
    System.out.println("chain entry: " + wikis[2].getEntry(topic) + "\n");
    String entry2 = "O ciclo da água descreve como a água se move pela Terra. Inclui fases de evaporação, condensação e precipitação.\n\n- Infiltração: parte da água penetra no solo e recarrega aquíferos.\n- Escorrimento superficial: a água escoa para rios e lagos.\n- Transpiração: plantas liberam vapor d'água para a atmosfera.\n- Sublimação: quando o gelo passa diretamente ao estado gasoso.";
    String post2 = wikis[2].createEntry(topic, entry2);
    System.out.println("hash post2: " + post2 + "\n");

    System.out.println("chain entry: " + wikis[3].getEntry(topic) + "\n");
    String entry3 = "O ciclo da água (ou ciclo hidrológico) descreve como a água se move pela Terra. Inclui fases de evaporação, condensação e precipitação.";
    String post3 = wikis[3].createEntry(topic, entry3);
    System.out.println("hash post3: " + post3 + "\n");

    // Simulando separação parcial da rede
    wikis[2].sendConsensus(topic, "localhost:" + wikis[0].wiki_port);
    wikis[2].sendConsensus(topic, "localhost:" + wikis[3].wiki_port);
    System.out.println("");

    wikis[3].sendConsensus(topic, "localhost:" + wikis[1].wiki_port);
    wikis[3].sendConsensus(topic, "localhost:" + wikis[2].wiki_port);
    System.out.println("");

    // Pioneiro (User0) e User1 dão likes nos post do User2 e User3 respectivamente
    wikis[0].likePost(post2, topic);
    System.out.println("");

    wikis[1].likePost(post3, topic);
    System.out.println("");

    Sync(wikis, topic);
    System.out.println("");


    System.out.println("chain consensus: " + wikis[0].getBlocks(topic) + "\n");
    System.out.println("chain entry: " + wikis[0].getEntry(topic) + "\n");

    for (int i = 0; i < wikis.length; i++) {
      wikis[i].stopHost();
    }
  }
}
