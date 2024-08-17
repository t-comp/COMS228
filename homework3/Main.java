package homework3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	// var to store the name of file
        String f;
        // StringBuilder to construct encoded message 
        StringBuilder encoded = new StringBuilder();
        // construct a new scanner for user input
        Scanner s = new Scanner(System.in);
        // user prompt for name of file
        System.out.print("Hello! Please enter the filename you would like to decode: ");
        // read name of file from user inpit
        f = s.next();
        // read lines from the file input
        ArrayList<String> lines = MsgTree.readInput(f);
        // construct encoded string from lines that are not empty
        for (int i = 0; i < lines.size() - 1; i++) {
        	// append current line to encoded message
            encoded.append(lines.get(i));
            // if it is not the last line in the encoding part
            if (i != lines.size() - 2) {
            	// add a newline
                encoded.append('\n');
            }
        }
        // create tree from the encoded string
        MsgTree tree = new MsgTree(encoded.toString());
        // print character codes header
        System.out.println("\nThe Character Codes: \n");
        // print character codes
        MsgTree.printCodes(tree, "");
        // get last line of encoded string
        String encodedS = lines.get(lines.size() - 1);
        // decoded encoded string
        MsgTree.decode(tree, encodedS);
        // close scanner
        s.close();
    }
}