package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileAnalyser {
    private static String performFileName;
    private static String destFileName;
    private static final int a_START = 97;
    private static final int A_START = 65;
    private static final int ALP_SIZE = 26;
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            System.out.println("Not enough arguments");
            return;
        }
        performFileName = args[0];
        destFileName = args[1];
        Map<Character, Integer> dictionary;
        try {
            dictionary = performFileAnalysis();
        } catch (IOException e) {
            System.out.println("Can't read from file");
            return;
        }
        try {
            putDictionary(dictionary);
        } catch (IOException e) {
            System.out.println("Can't write to file");
        }
    }
    private static Map<Character, Integer> performFileAnalysis() throws IOException {
        File performFile = new File(performFileName);
        if (performFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(performFile));
            String cur = bufferedReader.readLine();
            Map<Character, Integer> sorter = new HashMap<>(64);
            while (cur != null) {
                for (int i = 0; i < cur.length(); ++i)
                    sorter.compute(cur.charAt(i), (k, v) -> v == null ? 1 : v + 1);
                cur = bufferedReader.readLine();
            }
            return sorter;
        } else
            throw new IOException();
    }

    public static void putDictionary(Map<Character, Integer> dict) throws IOException {
        File file = new File(destFileName);
        file.createNewFile();
        PrintWriter printWriter = new PrintWriter(destFileName);
        for (int i = A_START; i < A_START + ALP_SIZE; ++i)
            printWriter.println("[" + (char)i + "]" + " : " + dict.getOrDefault((char)i, 0));
        for (int i = a_START; i < a_START + ALP_SIZE; ++i)
            printWriter.println("[" + (char)i + "]" + " : " + dict.getOrDefault((char)i, 0));
        printWriter.close();
    }
}