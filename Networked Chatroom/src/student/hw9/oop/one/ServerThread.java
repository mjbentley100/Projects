/**
 *  Helps with Privage messaging and processes usernames for server gui
 *
 * @author Mason Bentley
 * @version Hw 9, two
 * @bugs none
 *
 */

package student.hw9.oop.one;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Socket client;
    private String name;
    private JTextArea chat;
    private String[] parts;
    private final LinkedList<String> messages;

    public void newMessage(String new_message) {
        synchronized (messages) {
            messages.push(new_message);
        }
    }

    public ServerThread(Socket client, String name, JTextArea chat) {
        this.client = client;
        this.name = name;
        this.chat = chat;
        messages = new LinkedList<String>();
    }

    public void run() {
        String message = "Welcome " + name;
        chat.setText(message);

        try {
            PrintWriter out_stream = new PrintWriter(client.getOutputStream());
            InputStream in_stream = client.getInputStream();
            Scanner in = new Scanner(in_stream);
            int i = 0;


            while (!client.isClosed()) {
                if (i == 0) {
                    message = name;
                    out_stream.println(message);
                    out_stream.flush();
                    i++;
                }

                if (in_stream.available() > 0) {
                    if (in.hasNextLine()) {
                        message = in.nextLine();
                        chat.setText(chat.getText() + "\n" + message);

                    }
                }

                if (!messages.isEmpty()) {
                    String next = null;
                    synchronized (messages) {
                        next = messages.pop();
                    }


                    parts = next.split(" ");

                    if (parts[0].equals("/msg")) {
                        out_stream.println(next);
                        out_stream.flush();
                        System.out.println("ServerThread 1");
                    } else {
                        out_stream.println(name + "> " + next);
                        out_stream.flush();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
