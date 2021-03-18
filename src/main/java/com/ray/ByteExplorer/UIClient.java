package com.ray.ByteExplorer;

import com.github.tomaslanger.chalk.Chalk;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UIClient {

    private static Set<Integer> executedLines = new HashSet<>();

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket =  new ServerSocket(6969);
        File file = new File(
                "/Users/ray/Development/byte-explorer/src/main/java/com/ray/ByteExplorer/EvenOrOdd.java"
        );
        fileWriter(file,-1);
        System.out.println("Listening for connections from client");
        while (true){
            Socket client = serverSocket.accept();
            Scanner scanner = new Scanner(client.getInputStream());
            System.out.println("This ran");
            while (scanner.hasNext()){
                int mostRecent = scanner.nextInt();
                executedLines.add(mostRecent);
                fileWriter(file,mostRecent);
            }
        }
    }

    private static void fileWriter(File fileName, int recentlyExecuted){
        int lineNumber = 1;
        try (Scanner fileScanner = new Scanner(fileName)) {
            while (fileScanner.hasNext()) {
                if(recentlyExecuted== lineNumber){
                    System.out.println(Chalk.on(lineNumber+". "+fileScanner.nextLine()).green());
                } else {
                    if(executedLines.contains(lineNumber)){
                        System.out.println(Chalk.on(lineNumber+". "+fileScanner.nextLine()).yellow());
                    } else {
                        System.out.println(lineNumber+". "+fileScanner.nextLine());
                    }
                }
                lineNumber+=1;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
