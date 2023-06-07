import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Random;

public class DailyAdviceServer {
    final private String[] adviceList={
            "Take smaller bites",
            "Go for the tight jeans. No they do Not make you look fat.",
            "One word: inappropriate",
            "Just for today, be honest. Tell your boss what your *really* think",
            "You might want to rethink that haircut."
    };
    private final Random random = new Random();

    public void go(){
        /***
         * creates a server using a Try with Resources that has
         * a list of advice stored and selects one string at random
         * to send to the waiting class that has created a connection with
         * the port.
         */
        try(ServerSocketChannel serverChannel = ServerSocketChannel.open()){
            serverChannel.bind(new InetSocketAddress(5000));

            while(serverChannel.isOpen()){
                SocketChannel clientChannel = serverChannel.accept();
                PrintWriter writer = new PrintWriter(Channels.newOutputStream(clientChannel));
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String getAdvice() {
        int nextAdvice = random.nextInt(adviceList.length);
        return adviceList[nextAdvice];
    }

    public static void main(String[] args){
        new DailyAdviceServer().go();
    }
}
