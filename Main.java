import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Wiki wiki = new Wiki();

        System.out.print("Set a host directory (ex: /tmp/user0): ");
        String dir = sc.nextLine();
        wiki.startHost(dir);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Enter a new chain");
            System.out.println("2. Set user key");
            System.out.println("3. Post in a chain");
            System.out.println("4. Get chain entry");
            System.out.println("5. Send chain");
            System.out.println("6. Recive chain");
            System.out.println("0. Exit");
            System.out.print("Chose option: ");
            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    System.out.print("Chain name: ");
                    String chain = sc.nextLine();
                    System.out.print("Chain Shared key: ");
                    String key = sc.nextLine();
                    wiki.enterChain(chain, key);
                    break;

                case "2":
                    System.out.print("Passworld: ");
                    String secret = sc.nextLine();
                    wiki.setUserKeys(secret);
                    System.out.println("Public key: " + wiki.user_pubkey);
                    System.out.println("Private key: " + wiki.user_pvtkey);
                    break;

                case "3":
                    System.out.print("Chain name: ");
                    String topic = sc.nextLine();
                    System.out.println("Text: ");
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while (!(line = sc.nextLine()).equals("")) {
                        sb.append(line).append("\n");
                    }
                    wiki.createEntry(topic, sb.toString().trim());
                    break;

                case "4":
                    System.out.print("Chain name: ");
                    String t = sc.nextLine();
                    System.out.println("\nEntry:");
                    System.out.println(wiki.getEntry(t));
                    break;

                case "5":
                    System.out.print("Chain name: ");
                    String sendChain = sc.nextLine();
                    System.out.print("Destination Address (host:port): ");
                    String sendAddr = sc.nextLine();
                    wiki.sendConsensus(sendChain, sendAddr);
                    break;

                case "6":
                    System.out.print("Chain name: ");
                    String recvChain = sc.nextLine();
                    System.out.print("Origin address (host:port): ");
                    String recvAddr = sc.nextLine();
                    wiki.reciveConsensus(recvChain, recvAddr);
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
