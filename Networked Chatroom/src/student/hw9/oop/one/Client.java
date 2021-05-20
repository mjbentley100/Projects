/**
 *  Implements a Gui for the Client
 *
 * @author Mason Bentley
 * @version Hw 9, one
 * @bugs none
 *
 */

package student.hw9.oop.one;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private int port;
    protected Thread server;
    protected ServerThread server_thread;
    protected ClientGui gui;
    protected JTextArea chat;
    // private String host;
    private String name;

    public Client(int port, String name) {
        this.port = port;
        this.name = name;
        startClient();
    }


    public String getUserName() {
        return this.name;
    }

    private void startClient() {
        try {

            gui = new ClientGui();

            // Establish a connection with the server
            Socket client = new Socket("localhost", port);
            Thread.sleep(1000);

            // Create a thread to receive the messages from the server
            server_thread = new ServerThread(client, name, chat);
            server = new Thread(server_thread);
            server.start();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port;

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a port number: ");
        port = Integer.parseInt(scan.nextLine());

        String name = null;
        System.out.print("Enter a name: ");
        name = scan.nextLine();

        Client client = new Client(port, name);
    }

    public class ClientGui extends JFrame {

        JTextField input;


        public ActionListener button1 = new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                String message;
                message = input.getText();
                if (server.isAlive()) {
                    server_thread.newMessage(message);
                    //chat.setText(chat.getText() + "\n" + name +"> " + message);
                }
            }
        };



        public ClientGui() {
            JFrame f = new JFrame("Chat Room");
            f.setSize(500, 500);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            JPanel panel2 = new JPanel();


            chat = new JTextArea(25,45);
            panel.setLayout(new GridLayout());

            JButton submit = new JButton("submit");
            submit.addActionListener(button1);

            input = new JTextField(40);

            panel.add(input);
            panel.add(submit);
            //panel2.add(chat);
            //f.add(scroll);


            f.add(chat,BorderLayout.NORTH);
            f.add(panel, BorderLayout.SOUTH);

        }

    }


}
