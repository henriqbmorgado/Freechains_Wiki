import java.io.*;
import java.util.*;
import name.fraser.neil.plaintext.diff_match_patch;

public class Wiki {
  public String user_pvtkey;
  public String user_pubkey;
  public String wiki_port;

  public Wiki(String port) {
    this.wiki_port = port;
  }

  public Wiki() {
    this.wiki_port = "8330";
  }

  public ArrayList<String> readStream(InputStream input_stream) throws IOException {
    InputStreamReader input_stream_reader = new InputStreamReader(input_stream);
    BufferedReader buffered_reader = new BufferedReader(input_stream_reader);

    String line = buffered_reader.readLine();
    ArrayList<String> output = new ArrayList<>();

    if (line != null) {
      String[] parts = line.trim().split("\\s+");
      output = new ArrayList<>(Arrays.asList(parts));
      // System.out.println(output);
    }
    return output;
  }

  public String readStream_Singular(InputStream input_stream) throws IOException {
    InputStreamReader input_stream_reader = new InputStreamReader(input_stream);
    BufferedReader buffered_reader = new BufferedReader(input_stream_reader);

    String output = "";
    String line;

    while ((line = buffered_reader.readLine()) != null) {
      output = output + line + "\n";
      // System.out.println(output);
    }
    return output;
  }

  public Process Exec(String command) throws IOException {
    ProcessBuilder pb = new ProcessBuilder("sh", "-c", command);
    return pb.start();
  }

  public void setUserKeys(String key) {
    ArrayList<String> result;
    try {
      Process p = Exec("./freechains keys pubpvt '" + key.replace("'", "\\'") + "'");
      Thread.sleep(100);
      result = readStream(p.getInputStream());
      this.user_pubkey = result.getFirst();
      this.user_pvtkey = result.getLast();
    } catch (Exception e) {
      System.err.println("Faild to generate user privated key: " + e.getMessage());
    }
  }

  public void startHost(String dir) {
    try {
      Process p = Exec("./freechains-host start " + dir + " --port=" + this.wiki_port);
      Thread.sleep(2000);
      System.out.println("Freechains host started in: " + dir);
    } catch (Exception e) {
      System.err.println("Faild to start host: " + e.getMessage());
    }
  }

  public void stopHost() {
    try {
      Process p = Exec("./freechains-host stop --port=" + this.wiki_port);
      System.out.println("Freechains host on port: " + this.wiki_port + " stoped");
    } catch (Exception e) {
      System.err.println("Faild to stop freechains-host: " + e.getMessage());
    }
  }

  private String generateKey(String chain_sharedkey) throws Exception {
    List<String> hash_sharedkey;
    Process p = Exec("./freechains --host=localhost:" + this.wiki_port + " keys shared \"" + chain_sharedkey + "\"");
    Thread.sleep(100);
    hash_sharedkey = readStream(p.getInputStream());
    return hash_sharedkey.get(0);
  }

  public void enterChain(String chain_name, String chain_sharedkey) {
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " chains join \'" + chain_name + "\' " + chain_sharedkey);
      System.out.println("Chain " + chain_name + " joined");
    } catch (Exception e) {
      System.err.println("Faild to join/create the chain \"" + chain_name + "\": " + e.getMessage());
    }
  }

  public ArrayList<String> getChains() {
    ArrayList<String> chains;
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " chains list");
      Thread.sleep(100);
      chains = readStream(p.getInputStream());
      return chains;
    } catch (Exception e) {
      System.err.println("Faild to list chains: " + e.getMessage());
    }
    return null;
  }

  public void leaveChain(String chain_name) {
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " chains leave \'" + chain_name + "\'");
      System.out.println("Left chain " + chain_name);
    } catch (Exception e) {
      System.err.println("Faild to left the chain \"" + chain_name + "\": " + e.getMessage());
    }
  }

  public String getEntry(String topic) {
    ArrayList<String> blocks = getBlocks(topic);

    if (blocks == null || blocks.isEmpty()) {
      System.out.println("There is no posts about this topic");
      return "";
    }

    Object[] result = { "" };
    diff_match_patch dmp = new diff_match_patch();
    String patch_string = "";

    for (int i = 0; i < blocks.size(); i++) {
      if (i != 0) {
        patch_string = getPayload(blocks.get(i), topic);
      }
      try {
        LinkedList<diff_match_patch.Patch> patches = new LinkedList<>(dmp.patch_fromText(patch_string));
        result = dmp.patch_apply(patches, result[0].toString());
      } catch (Exception e) {
        System.err.println("Erro ao aplicar patch: " + e.getMessage());
      }
    }
    return result[0].toString();
  }

  public String createEntry(String topic, String new_text) {
    ArrayList<String> hash_block;

    String topic_text = getEntry(topic);
    diff_match_patch dmp = new diff_match_patch();

    LinkedList<diff_match_patch.Diff> diffs = dmp.diff_main(topic_text, new_text);
    LinkedList<diff_match_patch.Patch> patches = dmp.patch_make(diffs);

    String patch_string = dmp.patch_toText(patches);
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " chain \"" + topic + "\" post inline \"" + patch_string
              + "\" --sign=\'" + this.user_pvtkey + "\'");
      Thread.sleep(100);
      hash_block = readStream(p.getInputStream());
      if (!hash_block.isEmpty()) {
        System.out.println("Post created");
        return hash_block.get(0);
      }
    } catch (Exception e) {
      System.out.println("Faild to create post: " + e.getMessage());
    }
    System.out.println("Faild to create post");
    return null;
  }

  public void sendConsensus(String topic, String address) {
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " peer \'" + address + "\' send \'" + topic + "\'");
      Thread.sleep(100);
      System.out.println("Chain sended form: localhost:" + this.wiki_port + " to: " + address);
    } catch (Exception e) {
      System.out.println("Faild to send chain: " + e.getMessage());
    }
  }

  public void reciveConsensus(String topic, String address) {
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " peer \'" + address + "\' recv \'" + topic + "\'");
      Thread.sleep(100);
      System.out.println("Chain recived form: " + address);
    } catch (Exception e) {
      System.out.println("Faild to recive chain: " + e.getMessage());
    }
  }

  public void likePost(String hash, String topic) {
    try {
      Process p = Exec("./freechains --host=localhost:" + this.wiki_port + " chain \'" + topic + "\' like \'" + hash
          + "\' --sign=\'" + this.user_pvtkey + "\'");
      System.out.println("Like registered to block " + hash);
    } catch (Exception e) {
      System.out.println("Faild to dislike block " + hash + " : " + e.getMessage());
    }
  }

  public void dislikePost(String hash, String topic) {
    try {
      Process p = Exec("./freechains --host=localhost:" + this.wiki_port + " chain \'" + topic + "\' dislike \'" + hash
          + "\' --sign=\'" + this.user_pvtkey + "\'");
      System.out.println("Dislike registered to block " + hash);
    } catch (Exception e) {
      System.out.println("Faild to dislike block " + hash + " : " + e.getMessage());
    }
  }

  public ArrayList<String> getBlocks(String topic) {
    ArrayList<String> hashes;
    try {
      Process p = Exec("./freechains --host=localhost:" + this.wiki_port + " chain \'" + topic + "\' consensus");
      Thread.sleep(100);
      hashes = readStream(p.getInputStream());
      return hashes;
    } catch (Exception e) {
      System.err.println("Faild to get blocks: " + e.getMessage());
    }
    return null;
  }

  public ArrayList<String> getHeads(String topic, String arg) {
    ArrayList<String> hashes;
    try {
      Process p = Exec("./freechains --host=localhost:" + this.wiki_port + " chain \'" + topic + "\' heads " + arg);
      Thread.sleep(100);
      hashes = readStream(p.getInputStream());
      return hashes;
    } catch (Exception e) {
      System.err.println("Faild to get blocks: " + e.getMessage());
    }
    return null;
  }

  public String getPayload(String hash, String topic) {
    String payload;
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " chain \'" + topic + "\' get payload \'" + hash + "\'");
      Thread.sleep(100);
      payload = readStream_Singular(p.getInputStream());
      return payload;
    } catch (Exception e) {
      System.err.println("Faild to get payload: " + e.getMessage());
    }
    return null;
  }

  public int getReps(String hash, String topic) {
    ArrayList<String> rep;
    try {
      Process p = Exec(
          "./freechains --host=localhost:" + this.wiki_port + " chain \"" + topic + "\" reps \"" + hash + "\"");
      Thread.sleep(100);
      rep = readStream(p.getInputStream());
      return Integer.parseInt(rep.get(0));
    } catch (Exception e) {
      System.err.println("Faild to get block reps: " + e.getMessage());
    }
    return 31;
  }
}
