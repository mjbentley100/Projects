/**
 *  Implements server Gui
 *
 * @author Mason Bentley
 * @version Hw 9, two
 * @bugs none
 *
 */

package student.hw9.oop.two;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private int port;
    private ArrayList<ClientThread> clients;
    private String[] usernames;
    protected JList users;
    private int i = 0;
    serverGui Gui;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<ClientThread>();
        usernames = new String[200];
        Gui = new serverGui();

        // Begin the server socket
        ServerSocket server_socket = null;
        try {
            server_socket = new ServerSocket(port);

            // Waiting for client's connection request
            acceptClient(server_socket);
        } catch (IOException e) {
            System.out.println("Failed to start the server on port: " + port);
            e.printStackTrace();
        }
    }

    private void acceptClient(ServerSocket server_socket) {
        while(true) {
            try {
                // Accept new connection from the client
                Socket client = server_socket.accept();
                System.out.println("Accept new connection from: " + client.getRemoteSocketAddress());

                // Each client is running in the individual thread
                ClientThread client_thread = new ClientThread(this, client, users, i, usernames);
                Thread new_client = new Thread(client_thread);
                new_client.start();

                clients.add(client_thread);
                i++;


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ClientThread> getClients() {
        return clients;
    }


    public static void main(String[] args) {
        int port;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a port number: ");
        port = scan.nextInt();
        scan.close();

        Server server = new Server(port);


    }

    public class serverGui extends JFrame {


        public serverGui() {
            JFrame f = new JFrame("Server Client");
            f.setSize(250, 500);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();

            String[] dog = new String[300];


            users = new JList(dog);

            panel.add(users);

            f.add(panel, BorderLayout.WEST);


        }




    }



}
