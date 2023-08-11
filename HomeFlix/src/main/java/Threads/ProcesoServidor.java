package Threads;

import view.ControllerReproductor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcesoServidor extends Thread{

    @Override
    public void run () {

        String fromClient;
        String toClient;

        ServerSocket server = null;



        System.out.println("Esperando conexion en el puerto 8080");

        boolean run = true;

        try {
            server = new ServerSocket(8080);
            while (run){

                Socket client = server.accept();

                System.out.println("Se recibió una conexion en el puerto 8080");
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(ControllerReproductor.getPath());
                client.close();
                server.close();
                run = false;
            }

        }catch (IOException e){

            throw new RuntimeException(e);
        }
    }

    public static void play(){
        String fromClient;
        String toClient;

        ServerSocket server = null;

        try {
            server = new ServerSocket(8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Esperando conexion en el puerto 8080");

        boolean run = true;

        try {
            while (run){
                Socket client = server.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println("play");
                client.close();
                server.close();
                run = false;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void pause(){
        String fromClient;
        String toClient;

        ServerSocket server = null;

        try {
            server = new ServerSocket(8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Esperando conexion en el puerto 8080");

        boolean run = true;

        try {
            while (run){
                Socket client = server.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println("pause");
                client.close();
                run = false;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void restart(){
        String fromClient;
        String toClient;

        ServerSocket server = null;

        try {
            server = new ServerSocket(8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Esperando conexion en el puerto 8080");

        boolean run = true;

        try {
            while (run){
                Socket client = server.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println("restart");
                client.close();
                server.close();
                run = false;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
