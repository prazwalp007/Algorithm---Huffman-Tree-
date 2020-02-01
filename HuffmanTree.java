

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class HuffmanTree {
	TreeNode root;
	HuffmanTree() {
		root = null;
	}
	HashMap<String, String> HuffmanCodeLookUp = new HashMap();


	public void constructHuffmanTree(ArrayList<String> characters, ArrayList<Integer> freq) {
		//Create treeNodes for each characters

		ArrayList <TreeNode> charNodes = new ArrayList<TreeNode>();
		PriorityQueue<TreeNode> Q = new PriorityQueue<TreeNode>();


		for (int i= 0; i< characters.size();i++){
			TreeNode t = new TreeNode();
			t.setValue(characters.get(i));
			t.setFrequency(freq.get(i));
			charNodes.add(t);
		}

		//Create priority queue
		for (int i = 0; i < charNodes.size(); i++){
			Q.add(charNodes.get(i));
		}

		//Create Huffman Tree
		createHuffmanTree(Q);

		//Create a Lookup table for Huffman Code
		String code = "";
		findHuffmanCode(root, code);
	}

	public String encode(String humanMessage) {
		String encodedMessage = "";

		for (int i = 0; i < humanMessage.length(); i++){
			encodedMessage += HuffmanCodeLookUp.get(String.valueOf(humanMessage.charAt(i)));
		}

		return encodedMessage;
	}

	public String decode(String encodedMessage) {
		TreeNode node = root;
		String message = "";

		for (int i = 0; i < encodedMessage.length(); i++){
			char bits = encodedMessage.charAt(i);

			if (node != null) {
				if (bits =='0') {
					if (node.getLeftChild() != null)
						node = node.getLeftChild();
					if (node.getLeftChild() == null && node.getRightChild() == null) {
						message += node.getValue();
						node = root;
					}
				} else {
					if (node.getRightChild() != null)
						node = node.getRightChild();
					if (node.getLeftChild() == null && node.getRightChild() == null){
						message += node.getValue();
						node = root;
					}
				}

			}

		}

		return message;
	}

	private void createHuffmanTree(PriorityQueue<TreeNode> Q){

		//base cases
		if (Q.size() == 0){
			return;
		}


		if (Q.size() == 1){
			TreeNode node = new TreeNode();
			TreeNode leftChild = Q.poll();

			leftChild.setParent(node);
			node.setLeftChild(leftChild);
			root = node;
			return;
		}


		if (Q.size() == 2){
			TreeNode node = new TreeNode();
			TreeNode leftChild = Q.poll();
			TreeNode rightChild = Q.poll();

			leftChild.setParent(node);
			rightChild.setParent(node);

			node.setLeftChild(leftChild);
			node.setRightChild(rightChild);

			root = node;
			return;
		}

		//find two min
		TreeNode minNode1 = Q.poll();
		TreeNode minNode2 = Q.poll();

		//Combine min nodes and put that into Q
		TreeNode combinedMinNodes = new TreeNode();
		combinedMinNodes.setFrequency( minNode1.getFrequency()+ minNode2.getFrequency());
		minNode1.setParent(combinedMinNodes);
		minNode2.setParent(combinedMinNodes);
		combinedMinNodes.setLeftChild(minNode1);
		combinedMinNodes.setRightChild(minNode2);
		Q.add(combinedMinNodes);

		createHuffmanTree(Q);
	}



	private void findHuffmanCode(TreeNode node, String code){
		//traverse huffman tree
		if (node == null){
			return;
		}

		if (node.getLeftChild() == null && node.getRightChild() == null){
			HuffmanCodeLookUp.put(node.getValue(), code);

		}

		findHuffmanCode(node.getLeftChild(), code  + "0");
		findHuffmanCode(node.getRightChild(), code + "1");

	}



}
