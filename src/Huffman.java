// Alexander Agnitti
// 11/24/17

import java.util.*;
import java.io.*;

public class Huffman {
	
	private Tree m_root;
	private List<Tree> m_leafList = new ArrayList<Tree>();

	public static void main(String[] args) throws FileNotFoundException {
		(new Huffman()).go();
	}

	// Recieves the name of a text file and beings the processes
	// 		of building a Tree with the Huffman algorithm,
	//		displays the preordered tree, generates codes for each
	//		value, and recieves a binary code to decode.
	private void go() throws FileNotFoundException {

		Scanner in = new Scanner(System.in);
		System.out.print("\nEnter the name of a file: ");
		String file = in.nextLine().toLowerCase();

		List<Tree> initialTreeList = new ArrayList<Tree>();

		readFile(file, initialTreeList);
		pairNodes(initialTreeList);

		System.out.print("\nPreorder Huffman tree:");
		String preOrderedTree = m_root.treeToString();
		System.out.println(preOrderedTree);

		makeCodes();
		printCodes();
		
		System.out.print("\nEnter a string of binary digits: ");
		String digits = in.nextLine();
		decode(digits);
	}

	// Reads a given text file containing characters and their frequencies.
	// Places this information in a new HuffNode object.
	// Stores the HuffNodes in Trees and place the new tree in two places:
	//		m_leafList to be used in several other places,
	//		initialTreeList to be used to build the main tree.
	private void readFile(String fileName, List<Tree> initialTreeList) throws FileNotFoundException {
		
		System.out.printf("\nReading %s...\n", fileName);

		Scanner read = null;
		read = new Scanner(new File("../text/" + fileName));

		while (read.hasNext()) {
			String line = read.nextLine();
			String[] s = line.split(",", 2);

			char letter = s[0].charAt(0);
			Integer count = Integer.parseInt(s[1]);

			HuffNode n = new HuffNode(letter, count, null);			
			Tree t = new Tree(null, null, null, n);
			m_leafList.add(t);
			initialTreeList.add(t);
		}

		read.close();
	}

	// Pairs the two trees whose HuffNodes have the lowest value.
	// Continues until one tree is left, the root.
	// Along the way, connects trees to their new parents
	//		and creates new HuffNodes for tree values. 
	private void pairNodes(List<Tree> initialTreeList) {

		while (initialTreeList.size() > 1) {
			
			initialTreeList.sort((x, y) -> x.getValue().getCount().compareTo(y.getValue().getCount()));

			Tree treeA = initialTreeList.remove(0);
			Tree treeB = initialTreeList.remove(0);

			HuffNode nodeA = treeA.getValue();
			HuffNode nodeB = treeB.getValue();

			int sum = nodeA.getCount() + nodeB.getCount();

			HuffNode nodeC = new HuffNode('*', sum, null);

			Tree treeC = new Tree(null, treeA, treeB, nodeC);

			treeA.setParentTree(treeC);
			treeB.setParentTree(treeC);

			initialTreeList.add(treeC);
		}

		m_root = initialTreeList.remove(0);
		initialTreeList = null;
	}

	// Generates a code for each leaf through traceCode()
	// The codes are stored in a stack.
	private void makeCodes() {

		for (Tree t : m_leafList) {

			IStack<Integer> codeStack = new StackLL<Integer>();
			
			traceCode(t, codeStack);
			
			String s = codeStack.toString();
			t.getValue().setCode(s);
		}
	}

	// Recursively travels up the tree from a leaf until reaching
	//		the root (whose parent is null).
	// A left child is signalled by the value 0, with 1 for the right.
	private void traceCode(Tree t, IStack<Integer> codeStack) {

		Tree parent = t.getParentTree();
		
		if (parent != null) {
			if (t == parent.getLeftTree()) {
				codeStack.push(0);
			} else if (t == parent.getRightTree()) {
				codeStack.push(1);
			}
			traceCode(parent, codeStack);
		}
	}

	// Displays the codes for each HuffNode from longest to shortest.
	// As a result of the Huffman algo and that m_leafList is sorted 
	// 		in ascending order, print the reverse order if the list works.
	// Incidentally, this functions as an inorder traversal where all
	// 		non-leaf trees are omitted. 
	private void printCodes() {
		
		int leaves = m_leafList.size() - 1;

		System.out.println("\nCodes:");
		for (int i = leaves; i >= 0; i--) {
			System.out.println(m_leafList.get(i).getValue());
		}
	}

	// Takes a string of binary digits and travels from the root
	// tree until a HuffNode with a letter value is found.
	// Continues for the remaining digits.
	private void decode(String digits) {
		
		char[] digitArray = digits.toCharArray();

		StringBuffer buf = new StringBuffer();

		Tree t = m_root;
		for (char c : digitArray) {
			if (c == '0') {
				t = t.getLeftTree();
			} else if (c == '1') {
				t = t.getRightTree();
			}

			if (t.getValue().getLetter() != '*') {
				buf.append(t.getValue().getLetter());
				t = m_root;
			}
		}

		String message = buf.toString();
		System.out.printf("Decoded as: %s\n", message);
	}
}
