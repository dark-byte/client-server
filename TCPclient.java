import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {

    public static void main(String[] args) throws IOException {
        DataOutputStream Out;
        DataInputStream In;
        Scanner Scan = new Scanner(System.in);
        Socket socket = new Socket("192.168.188.105", 4000);
        System.out.println("client connected to server");
        System.out.print("\n enter the filename to request \n");
        String filename = Scan.nextLine();
        In = new DataInputStream(socket.getInputStream());
        Out = new DataOutputStream(socket.getOutputStream());
        Out.writeUTF(filename);
        String filecontent = In.readUTF();
        if (filecontent.length() > 0) {
            System.out.println(filecontent);
        } else {
            System.out.println("FILE IS EMPTY");
        }
        Scan.close();
        socket.close();
    }

}