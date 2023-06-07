import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SimpleChatClientA {
    private JTextArea incoming;
    private JTextField outgoing;
    private PrintWriter writer;
    private BufferedReader reader;

    public void go(){
        /**
         * set ups the network connection with local server
         * creates GUI display initializes the outing JTextField
         * adds and actionlistener to the send button
         **/
        // call the setupNetworking() method
        setUpNetworking();
        // make gui and register a listener with the send button
        JFrame frame = new JFrame("Simple Chat Client");

        JPanel contents = new JPanel();

        JScrollPane scroller = createScrollableTextArea();
        contents.add(scroller);

        outgoing = new JTextField(20);
        contents.add(outgoing);

        JButton send = new JButton("Send");
        send.addActionListener(e->sendMessage());
        contents.add(send);

        // create a new job an inner class which is a runnable is to read from the server's socket
        // stream displaying any incoming messages
        // we start this job using a single thread executor since we know we want to run only this one
        // job
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new IncomingReader());

        frame.getContentPane().add(contents);
        frame.setVisible(true);
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JScrollPane createScrollableTextArea() {
        /**
         * separate method to create the incoming text area from the server
         * */
        incoming = new JTextArea(15,30);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane scroller = new JScrollPane(incoming);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        return scroller;
    }

    private void setUpNetworking() {
        /**
         * knowing the IP address and port number to set up InetSocketAddress
         * creates a SocketChannel to open a connection
         * Sets ups a bufferedReader to read from the server
         * and PrintWriter to send information to the server
         * */
        // open a SocketChannel to the server
        try{
            InetSocketAddress serverAdd = new InetSocketAddress("127.0.0.1", 5000);
            SocketChannel socketChannel = SocketChannel.open(serverAdd);
            reader = new BufferedReader(Channels.newReader(socketChannel, UTF_8));
            writer = new PrintWriter(Channels.newWriter(socketChannel,UTF_8));
            System.out.println("Networking established.");
        }catch (IOException e){
            e.printStackTrace();
        }
        // make a printwriter and assign to writer instance variable
    }

    private void sendMessage(){
        /***
         * gets the text from the outgoing JTextField
         * and prints it to the server using the println()
         * flushes the PrintWriter and prepares to send the next message
         */
        /
        // get the text from the text field and
        writer.println(outgoing.getText());
        writer.flush();
        outgoing.setText("");
        outgoing.requestFocus();
        // send it to the server using the writer (a PrintWriter)

    }
    public class IncomingReader implements Runnable{
        /***
         * runnable used by the thread in the go() method
         * checks if there is any input from the server
         * if there is it stores in message and appends() the
         * message to the incoming window that displays to the user
         */

        public void run() {
            String message;
            try{
                while((message = reader.readLine())!=null){
                    System.out.println("read " +message);
                    incoming.append(message+"\n");
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        new SimpleChatClientA().go();
    }
}
