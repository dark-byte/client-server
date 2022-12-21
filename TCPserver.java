import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TCPserver extends Thread {

    public static void main(String[] args) throws IOException {
        DataOutputStream Out = null;
        DataInputStream In;
        while(true)
        {
            try {
                ServerSocket server = new ServerSocket(4000, 1);
                System.out.println("server waiting for client");
                Socket s_socket = server.accept();
                In = new DataInputStream(s_socket.getInputStream());
                Out = new DataOutputStream(s_socket.getOutputStream());
                ThreadHandler thread = new ThreadHandler(In, Out, s_socket);
                thread.start();
                server.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                Out.writeUTF("FILE DOESNT EXISTS");
            }
        }
    }
}

class ThreadHandler extends Thread {
    DataInputStream In;
    DataOutputStream Out;
    Socket s;

    ThreadHandler(DataInputStream In, DataOutputStream Out, Socket s) {
        this.In = In;
        this.Out = Out;
        this.s = s;
    }

    public void run() {
        try {
            System.out.println("client connected");

            String filename = In.readUTF();
            System.out.println("file requested is:" + filename);
            byte[] filedata = Files.readAllBytes(Paths.get(filename));
            String filecontent = new String(filedata);
            Out.writeUTF(filecontent);
            System.out.println("FILE SENT SUCCESFULLY");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}