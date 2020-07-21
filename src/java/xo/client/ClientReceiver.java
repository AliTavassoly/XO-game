package xo.client;

import java.io.InputStream;

public class ClientReceiver extends Thread{
    private InputStream inputStream;

    public ClientReceiver(InputStream inputStream){
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {

        }
    }
}
