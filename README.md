## Vehicle Security Project by Swathi, Achyuth and Tanisha

## Introduction

The following Java code provides a comprehensive illustration of a blockchain implementation tailored for Vehicle-to-Vehicle (V2V) communication. This technical document dissects the code, explaining its fundamental components and functionalities, and demonstrates how blockchain technology can enhance secure and decentralized communication between vehicles.

This program served as my final project in a summer course "Interconnected Vehicle Security". This was my first time diving into the wondorous world of blockchain and greatly helped me stengthen my concepts and work hands-on on a potential application for it. Vehicular security is an up-and-coming and relevant branch of cybersecurity. As our vehicles become smarter, the risk of them getting hacked also increases. 

## 1. Block Class

The `Block` class is the cornerstone of the blockchain. It encapsulates essential information such as the block's index, timestamp, previous hash, current hash, messages, and a nonce value. The class features methods to calculate the hash of the block, mine the block, and generate a nonce.

## 2. Blockchain Class

The `Blockchain` class orchestrates the progression of blocks. It maintains a chain of interconnected blocks, tracks network members, and defines the mining difficulty level. This class encompasses methods to create the genesis block, add network members, incorporate new blocks containing messages, and verify the integrity of the blockchain.

## 3. Main Class (V2VCommunications)

The `V2VCommunications` class serves as the main driver of the blockchain application. It initializes the network, provides a user-friendly menu-driven interface, and enables users to send messages, visualize the blockchain's status, and exit the program. 

## Explanation of Key Concepts

### 1. Block Mining and Hashing

- The `calculateHash()` method employs the SHA-256 hashing algorithm to generate the hash of the block.
- The `mineBlock()` method engages in an iterative process, adjusting the nonce value until the hash of the block fulfills a predetermined target pattern based on the mining difficulty.
- A nonce is a variable miners tweak to produce a hash that fits specific conditions, like having a certain number of leading zeros. Miners change the nonce repeatedly, recalculating the hash each time, until the desired condition is met. This process adds security by making it computationally demanding to alter block data and ensures the legitimacy of blockchain transactions.

### 2. Blockchain Creation and Validation

- The `Blockchain` constructor initializes the blockchain with a genesis block, which represents the inception of the blockchain.
- The `createGenesisBlock()` method crafts the first block, featuring a predefined message ("Genesis block").
- The `addMember()` method facilitates the inclusion of vehicles as network members.
- The `addBlock()` method constructs and mines new blocks containing messages. Each block is connected to the previous one, forming a coherent chain.

### 3. Blockchain Validation

- The `isChainValid()` method evaluates the blockchain's integrity by validating hashes and verifying links to previous blocks.

### 4. User Interaction

- The `V2VCommunications` class presents a user-friendly interface:
  - Vehicle information is collected, and vehicles are integrated as network members.
  - Users can send messages between vehicles.
  - The blockchain's status is displayed, including its validity and the detailed attributes of each block.

## Usage Instructions

1. Compile and execute the program.
2. Input the number of participating vehicles and their respective names.
3. Navigate through the menu to:
   - Send messages between vehicles.
   - Visualize the blockchain's status and validity.
   - Exit the program.

## Benefits and Potential Enhancements

- This code offers a foundational understanding of blockchain technology, making it an excellent educational resource.
- Its simplicity facilitates the comprehension of core blockchain principles.
- The implementation can be extended with advanced features such as data encryption and more sophisticated consensus mechanisms.
