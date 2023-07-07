import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Block {
    private int index;
    private Date timestamp;
    private String previousHash;
    private String hash;
    private List<String> messages;
    private String nonce;

    public Block(int index, Date timestamp, String previousHash, List<String> messages) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.messages = messages;
        this.nonce = "";
        this.hash = calculateHash();
    }

    // Calculates the hash of the block based on its properties
    public String calculateHash() {
        String dataToHash = index + timestamp.toString() + previousHash + messages.toString() + nonce;
        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(dataToHash.getBytes());

            for (byte hashByte : hashBytes) {
                sb.append(String.format("%02x", hashByte & 0xff));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    // Mines the block by finding a nonce that satisfies the given difficulty level
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce = generateNonce();
            hash = calculateHash();
        }

        System.out.println("Block mined: " + hash);
    }

    // Generates a nonce (number used once) for mining the block
    private String generateNonce() {
        return String.valueOf(System.currentTimeMillis());
    }

    // Getters for the block properties
    public int getIndex() {
        return index;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public List<String> getMessages() {
        return messages;
    }
}

class Blockchain {
    private List<Block> chain;
    private List<String> members;
    private int difficulty;

    public Blockchain(int difficulty) {
        chain = new ArrayList<>();
        members = new ArrayList<>();
        this.difficulty = difficulty;
        chain.add(createGenesisBlock());
    }

    // Creates the genesis block (the first block) of the blockchain
    private Block createGenesisBlock() {
        List<String> messages = new ArrayList<>();
        messages.add("Genesis block");

        return new Block(0, new Date(), "0", messages);
    }

    // Adds a new member to the V2V communication network
    public void addMember(String member) {
        members.add(member);
    }

    // Adds a new block to the blockchain with the provided messages
    public void addBlock(List<String> messages) {
        Block latestBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(latestBlock.getIndex() + 1, new Date(), latestBlock.getHash(), messages);
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    // Checks if the blockchain is valid by verifying hashes and previous hash links
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }

        return true;
    }

    // Getter for the blockchain
    public List<Block> getChain() {
        return chain;
    }
}

public class V2VCommunications {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Blockchain blockchain = new Blockchain(4);

        // Get the number of vehicles participating in V2V communication
        System.out.print("Enter the number of vehicles participating in V2V communication: ");
        int numVehicles = scanner.nextInt();
        scanner.nextLine();

        // Add each vehicle as a member of the blockchain network
        for (int i = 0; i < numVehicles; i++) {
            System.out.print("Enter the name of vehicle " + (i + 1) + ": ");
            String vehicle = scanner.nextLine();
            blockchain.addMember(vehicle);
        }

        int choice = 0;

        // Main menu loop
        while (choice != 3) {
            System.out.println("\nMenu:");
            System.out.println("1. Send a message between vehicles");
            System.out.println("2. Print the V2V communication blockchain");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Send a message between vehicles
                    System.out.print("Enter the index of the sender vehicle (1-" + numVehicles + "): ");
                    int senderIndex = scanner.nextInt();
                    scanner.nextLine();

                    if (senderIndex < 1 || senderIndex > numVehicles) {
                        System.out.println("Invalid vehicle index.");
                        break;
                    }

                    System.out.print("Enter the index of the recipient vehicle (1-" + numVehicles + "): ");
                    int recipientIndex = scanner.nextInt();
                    scanner.nextLine();

                    if (recipientIndex < 1 || recipientIndex > numVehicles) {
                        System.out.println("Invalid vehicle index.");
                        break;
                    }

                    System.out.print("Enter the message: ");
                    String message = scanner.nextLine();

                    // Create a list of messages and add the new message to it
                    List<String> messages = new ArrayList<>();
                    messages.add("From Vehicle " + senderIndex + " to Vehicle " + recipientIndex + ": " + message);

                    // Add the block containing the message to the blockchain
                    blockchain.addBlock(messages);
                    break;
                case 2:
                    // Print the V2V communication blockchain
                    System.out.println("\nV2V Communication Blockchain Status:");
                    System.out.println("Blockchain is valid: " + blockchain.isChainValid());

                    // Iterate over the blocks in the blockchain and print their details
                    for (Block block : blockchain.getChain()) {
                        System.out.println("\nBlock Index: " + block.getIndex());
                        System.out.println("Timestamp: " + block.getTimestamp());
                        System.out.println("Previous Hash: " + block.getPreviousHash());
                        System.out.println("Hash: " + block.getHash());
                        System.out.println("Messages:");
                        for (String msg : block.getMessages()) {
                            System.out.println("- " + msg);
                        }
                    }
                    break;
                case 3:
                    // Exit the program
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

        scanner.close();
    }
}
