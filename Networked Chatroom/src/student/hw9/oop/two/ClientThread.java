/**
 * Helps to manage private messaging and gui messaging for the client
 *
 * @author Mason Bentley
 * @version Hw 9, two
 * @bugs none
 *
 */
package student.hw9.oop.two;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientThread implements Runnable {
    private Server server;
    private Socket client;
    private PrintWriter out;
    private String name;
    private JList users;
    private String[] usernames;
    private int j;

    public ClientThread(Server server, Socket client, JList users, int i, String[] usernames) {
        this.server = server;
        this.client = client;
        this.users = users;
        this.j = i;
        this.usernames = usernames;
    }

    public String getName() {
        return name;
    }


    public void run() {
        try {
            this.out = new PrintWriter(client.getOutputStream(), false);
            Scanner in = new Scanner(client.getInputStream());
            int i = 0;
            String[] parts;
            String pvtMessage;
            int k = 3;



            while(!client.isClosed()) {


                if (in.hasNextLine()) {
                    String input = in.nextLine();


                    //System.out.println(in.nextLine());
                    for (ClientThread c : server.getClients()) {
                        PrintWriter cout = c.getWriter();

                        parts = input.split(" ");

                        if (parts[0].equals("/msg")){
                            if (c.getName().equals(parts[1]) || c.getName().equals(name)){


                                cout.write(name + ">" + input + "\r\n");
                                cout.flush();
                            }
                        } else if (cout != null && i != 0) {
                            cout.write(input + "\r\n");
                            cout.flush();

                        }

                        if (i == 0) {
                            name = (input);
                            usernames[j] = name;
                            users.setListData(usernames);
                            i++;
                        }

                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getWriter() {
        return out;
    }
}
