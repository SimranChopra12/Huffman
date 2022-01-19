//package Assignment4;
/**Simran Chopra
 * B00876439
 * Assignment4
 */

import java.util.Scanner;
import java.util.*;

import java.io.File;

import java.io.*;

public class Huffman {




    public static void main(String[] args) throws FileNotFoundException {
        //file not found exception in case the file path is incorrect/file doesnt exist
        Queue<BinaryTree<Pair>> S = new LinkedList<>();
        Queue<BinaryTree<Pair>> T = new LinkedList<>();
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the filename to read from: ");
        //user enters the file name
        String filename = keyboard.nextLine();

        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        StringTokenizer token;
        while (inputFile.hasNext()) {
            String line = inputFile.nextLine();
            token = new StringTokenizer(line, "\t");
            //taking input from the file
            String a = token.nextToken();
            String n = token.nextToken();

            char alphabet = a.charAt(0);
            //converting string to double
            double number = Double.parseDouble(n);

            //Pair object
            Pair p = new Pair(alphabet, number);
            BinaryTree<Pair> A = new BinaryTree<>();
            A.setData(p);
            S.add(A);

        }
        inputFile.close();
        BinaryTree<Pair> A = new BinaryTree<>();
        BinaryTree<Pair> B = new BinaryTree<>();

        while (!S.isEmpty()) {
            if (T.isEmpty()) {
                //Since T is empty, A and B are respectively the front and next to front entries of S.
                A = S.poll();
                B = S.poll();

            } else {
                //the smaller weight tree of the trees in front of S and in front of T. We will dequeue A.
                if (S.peek().getData().getProb() <= T.peek().getData().getProb()) {
                    A = S.poll();
                } else {
                    A = T.poll();
                }

                 //the smaller weight tree of the trees in front of S and in front of T. We will dequeue B.
                if (S.isEmpty() && !T.isEmpty()) {
                    B = T.poll();
                } else if (T.isEmpty() && !S.isEmpty()) {
                    B = S.poll();
                } else if (S.peek().getData().getProb() <= T.peek().getData().getProb()) {
                    B = S.poll();
                } else {
                    B = T.poll();
                }
            }

            //constructing a new tree P
            BinaryTree<Pair> P = new BinaryTree<>();
            Pair pair = new Pair('0', A.getData().getProb() + B.getData().getProb());
            P.setData(pair);
            P.attachLeft(A);
            P.attachRight(B);
            //Enqueue tree P to queue T
            T.add(P);

        }

        while (T.size() > 1) {
            BinaryTree<Pair> P = new BinaryTree<>();
            A = T.poll();
            B = T.poll();
            Pair pair = new Pair('0', A.getData().getProb() + B.getData().getProb());
            P.setData(pair);
            P.attachLeft(A);
            P.attachRight(B);
            T.add(P);

        }



        BinaryTree<Pair>Huffman = T.poll();

        //taking input from user
        System.out.println("Enter a line of text (uppercase letters only):");
        Scanner in = new Scanner(System.in);
        String word= in.nextLine();
        //converting to arraylist
        ArrayList<String> huffmanCodes = new ArrayList<>(Arrays.asList(findEncoding(Huffman)));

        String answer = "";
        for(int i = 0; i < word.length(); i++) {
            char a = word.charAt(i);
            //if letters are not uppercase
            if (a >= 65 && a <= 90) {
                answer += huffmanCodes.get(a - 65);
            } else {
                answer += a;
            }
        }


        //calling methods
        System.out.print("Here's the encoded line:");
        System.out.print(answer);

        System.out.println("\n");

        System.out.print("Here's the decoded line:");
        System.out.println(findDecoding(Huffman,answer));
    }

    //methods given to us for encoding:

    private static String[] findEncoding(BinaryTree<Pair> bt) {
        String[] result = new String[26];
        findEncoding(bt, result, "");
        return result;
    }


    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix) {
        if (bt.getLeft() == null && bt.getRight() == null) {
            a[bt.getData().getValue() - 65] = prefix;
        } else {
            //recursive call
            findEncoding(bt.getLeft(), a, prefix + "0");
            findEncoding(bt.getRight(), a, prefix + "1");
        }
    }


    /**
     * Used for decoding the encoded string
     * @param A Binary tree of type Pair
     * @param a the encoded String that we will decode
     * @return the decoded String
     */
    private static String findDecoding(BinaryTree<Pair> A, String a) {


        BinaryTree<Pair>h= A;
        String decode = "";
        for(int i =0 ; i< a.length(); i++ ){
            if (a.charAt(i) != '0' && a.charAt(i) != '1') {
                decode += a.charAt(i);
                continue;
            }
            //if the number is 0 or 1, we will go left or right accordingly
            if(a.charAt(i)== '0') {
               h=  h.getLeft();
            }
            else if(a.charAt(i)== '1') {
                h=h.getRight();
            }
            if (h.getLeft()==null && h.getRight()==null){
                decode+= h.getData().getValue();
                //starting at the root again
                h = h.root();
            }
        }



        return decode;


    }
}



