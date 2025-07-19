import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Wiki wiki = new Wiki("8330");

    System.out.print("Set a host directory (ex: ./myWiki/): ");
    String dir = sc.nextLine();
    wiki.startHost(dir);

    System.out.print("Set user key (ex: pass123): ");
    String secret = sc.nextLine();
    wiki.setUserKeys(secret);
    System.out.println("Public key: " + wiki.user_pubkey);
    System.out.println("Private key: " + wiki.user_pvtkey);

    while (true) {
      System.out.println("\n=== Freechains Wiki ===");
      System.out.println("1. Join/Create a new chain");
      System.out.println("2. Leave chain");
      System.out.println("3. List joined chains");
      System.out.println("4. Post in a chain");
      System.out.println("5. Get chain entry");
      System.out.println("6. See chains blocks");
      System.out.println("7. (Dis)Like post");
      System.out.println("8. Get reps");
      System.out.println("9. Conection");
      System.out.println("0. Exit");
      System.out.print("Chose option: ");
      String opt = sc.nextLine();

      switch (opt) {
        case "1":
          System.out.print("Chain name (ex: #abc): ");
          String c1 = sc.nextLine();
          System.out.print("Chain Shared key (ex: A5B97DDAFD7D21350... [lenth=64]): ");
          String key = sc.nextLine();
          wiki.enterChain(c1, key);
          break;

        case "2":
          System.out.print("Chain name (ex: #abc): ");
          String c2 = sc.nextLine();
          wiki.leaveChain(c2);
          break;

        case "3":
          System.out.println("\nChains:");
          System.out.println(wiki.getChains());
          break;

        case "4":
          System.out.print("Chain name (ex: #abc): ");
          String c3 = sc.nextLine();
          System.out.println("Text (enter an empty line send text): ");
          StringBuilder sb = new StringBuilder();
          String line;
          while (!(line = sc.nextLine()).equals("")) {
            sb.append(line).append("\n");
          }
          wiki.createEntry(c3, sb.toString().trim());
          break;

        case "5":
          System.out.print("Chain name (ex: #abc): ");
          String c4 = sc.nextLine();
          System.out.println("\nEntry:");
          System.out.println(wiki.getEntry(c4));
          break;

        case "6":
          System.out.println("1. See chain Consensus");
          System.out.println("2. See chain blocked Heads");
          System.out.println("0. Back");
          System.out.print("Chose option: ");
          String opt2 = sc.nextLine();
          switch (opt2) {
            case "1":
              System.out.print("Chain name (ex: #abc): ");
              String c5 = sc.nextLine();
              System.out.println("\nConsensus:");
              System.out.println(wiki.getBlocks(c5));
              break;

            case "2":
              System.out.print("Chain name (ex: #abc): ");
              String c6 = sc.nextLine();
              System.out.println("\nBlocked Heads:");
              System.out.println(wiki.getHeads(c6, "blocked"));
              break;

            case "0":
              break;

            default:
              System.out.println("Invalid option.");
          }
          break;

        case "7":
          System.out.println("1. Like");
          System.out.println("2. Dislike");
          System.out.println("0. Back");
          System.out.print("Chose option: ");
          String opt3 = sc.nextLine();
          switch (opt3) {
            case "1":
              System.out.print("Chain name (ex: #abc): ");
              String c7 = sc.nextLine();
              System.out.print("Post hash: ");
              String hash1 = sc.nextLine();
              wiki.likePost(hash1, c7);
              break;

            case "2":
              System.out.print("Chain name (ex: #abc): ");
              String c8 = sc.nextLine();
              System.out.print("Post hash: ");
              String hash2 = sc.nextLine();
              wiki.dislikePost(hash2, c8);
              break;

            case "0":
              break;

            default:
              System.out.println("Invalid option.");
          }
          break;

        case "8":
          System.out.print("Chain name (ex: #abc): ");
          String c9 = sc.nextLine();
          System.out.print("Post hash/User public key: ");
          String hash3 = sc.nextLine();
          int rep = wiki.getReps(hash3, c9);
          if (rep >= 31) {
            System.out.println("Faild to get \'" + hash3 + "\' reps.");
          } else {
            System.out.println("\nReps: " + rep);
          }
          break;

        case "9":
          System.out.println("1. Send chain");
          System.out.println("2. Recive chain");
          System.out.println("0. Back");
          System.out.print("Chose option: ");
          String opt4 = sc.nextLine();
          switch (opt4) {
            case "1":
              System.out.print("Chain name (ex: #abc): ");
              String sendChain = sc.nextLine();
              System.out.print("Destination Address (host:port): ");
              String sendAddr = sc.nextLine();
              wiki.sendConsensus(sendChain, sendAddr);
              break;

            case "2":
              System.out.print("Chain name (ex: #abc): ");
              String recvChain = sc.nextLine();
              System.out.print("Origin address (host:port): ");
              String recvAddr = sc.nextLine();
              wiki.reciveConsensus(recvChain, recvAddr);
              break;

            case "0":
              break;

            default:
              System.out.println("Invalid option.");
          }
          break;

        case "0":
          wiki.stopHost();
          System.out.println("Exiting.");
          return;

        default:
          System.out.println("Invalid option.");
      }
    }
  }
}
