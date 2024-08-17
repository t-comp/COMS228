package homework3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Taylor Bauer
 * 
 * 
 * Citations:
 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println,
 * https://codehs.com/tutorial/ryan/add-color-with-ansi-in-javascript,
 * https://stackoverflow.com/questions/12899953/in-java-how-to-append-a-string-more-efficiently
 */

public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;

	/**
	 * Constructs a MsgTree from the encoding string iteratively.
	 * 
	 * In the construction of a tree, the character '^' an indicates an internal
	 * node, and other characters indicate a lead node. The string is read from left
	 * to right and creates new nodes, linking them to the appropriate left or right
	 * children.
	 * 
	 * A stack is used to manage the building process where the root is the first
	 * character of encoding string and pushed onto the stack.
	 * 
	 * @param encodingString - the string that encodes the tree structure. The first
	 *                       character is the root and the following are either an
	 *                       internal node ('^') or a leaf node.
	 */
	public MsgTree(String encodingString) {
		// check if the encoding string has validity
		if (encodingString == null || encodingString.length() < 2) {
			return;
		}
		// create a new stack for tree building management
		Stack<MsgTree> s = new Stack<>();

		// initialize the root node with the first character
		this.payloadChar = encodingString.charAt(0);
		// push the root node onto the stack
		s.push(this);
		// iterate through the rest of the encoding string
		for (int i = 1; i < encodingString.length(); ++i) {
			// get node at the top of the stack
			MsgTree current = s.peek();
			// grab the current character
			Character c = encodingString.charAt(i);
			// create a new node for the message tree w/ the char
			MsgTree node = new MsgTree(c);
			// assign the node to the left or right child
			if (current.left == null) {
				// left
				current.left = node;
			} else {
				// right
				current.right = node;
				// remove current node from stack if both children have been assigned
				s.pop();
			}
			// if the node is an internal node, push onto stack
			if (node.payloadChar == '^') {
				s.push(node);
			}
		}
	}

	/**
	 * Constructs a MsgTree node with the given character.
	 * 
	 * @param payloadChar - the character that will be stored in the node. Can be
	 *                    any character for leaf nodes or '^' for internal nodes.
	 * 
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	}

	/**
	 * Prints characters and their binary codes using pre-order traversal.
	 * 
	 * @param root - the root of the tree.
	 * @param code - the current binary code amassed.
	 */
	public static void printCodes(MsgTree root, String code) {
		// print header table once, when root node is empty
		if (code.equals("")) {
			// print header text in purple
			System.out.println(Colors.DARKERPURPLE.getColor() + "character   code" + Colors.OG.getColor());
			System.out.println("----------------");
		}
		// base case: return if root is null
		if (root == null) {
			return;
		}
		// if a leaf node, print the character and its node
		if (root.payloadChar != '^') {
			// turn char into string
			String ch = Character.toString(root.payloadChar);
			// special case: newline char
			if (root.payloadChar == '\n') {
				// replace newline char
				ch = "\\n";
			} else {
				// print char w/ corresponding binary code
				System.out.printf(" %-11s %s%n", ch, code);
			}
		}
		// recursively go through left subtree, add '0' to code
		printCodes(root.left, code + "0");
		// recursively go through right subtree, add '1' to code
		printCodes(root.right, code + "1");
	}

	/**
	 * Decodes the encoded message using the tree.
	 * 
	 * @param root - the root of the tree.
	 * @param encodedMessage - the message to be decoded.
	 */

	public static void decode(MsgTree root, String encodedMessage) {
		// StringBuilder to store decoded message - inspired by StackOverflow
		StringBuilder decoded = new StringBuilder();
		// start from the root of tree
		MsgTree current = root;
		// iterate through each char (bit) in encoded message
		for (int i = 0; i < encodedMessage.length(); ++i) {
			// current char (bit)
			Character bit = encodedMessage.charAt(i);
			// if the bit is '0'
			if (bit == '0') {
				// go left
				current = current.left;
			} else if (bit == '1') {
				// otherwise, go right
				current = current.right;
			}
			// if it is a leaf node
			if (current.isLeaf()) {
				// append the character to the decoded message - inspired by StackOverflow
				decoded.append(current.payloadChar);
				// reset to root for next char
				current = root;
			}
		}
		// print decoded message w/ ANSI escape codes - (idk if you can see this)
		System.out.println("\nThe Decoded Message: " + Colors.LIGHTERPURPLE.getColor() + decoded.toString());
	}

	/**
	 * Private helper method to check if the node is a leaf, meaning it has no
	 * children.
	 * 
	 * @return true if the node is leaf, otherwise false.
	 */
	private boolean isLeaf() {
		return this.left == null && this.right == null;
	}

	/**
	 * Reads input from a specified file and returns the lines as an ArrayList of
	 * Strings.
	 * 
	 * @param file - the name of the file to read
	 * @return an ArrayList of String that contains the lines of the file.
	 */
	public static ArrayList<String> readInput(String file) {
		// list to store the lines
		ArrayList<String> lines = new ArrayList<>();
		// exception handling
		try {
			// create a new file object
			File f = new File(file);
			// create a new scanner object to parse the file
			Scanner s = new Scanner(f);
			// while there is more lines of the file to be read
			while (s.hasNextLine()) {
				// read the lines and add it to the list
				lines.add(s.nextLine());
			}
			// close scanner
			s.close();
		} catch (FileNotFoundException e) {
			// error message in case file is not found
			System.out.println("Oops, sorry! Unfortunately, the file entered was not found.");
		}
		// return the list of lines
		return lines;
	}
}