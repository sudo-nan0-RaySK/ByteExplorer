package com.ray.ByteExplorer;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EvenOrOdd {

    private Socket socket;
    private PrintWriter writer;

    public EvenOrOdd(){
        try{
            this.socket = new Socket("localhost",6969);
            writer = new PrintWriter(socket.getOutputStream(),true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void isEvenOrOdd(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number to check if it is even or odd (-1 to end program)");

        while(true){
            int number = sc.nextInt();
            if(number==-1){
                return;
            }
            if((number%2)==0){
                System.out.println("The number is Even");
                System.out.println("Even block is executed");
            }
            else {
                System.out.println("The number is Odd");
                System.out.println("Odd block is executed");
            }
        }
    }

}
