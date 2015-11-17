import it.sauronsoftware.ftp4j.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

/**
 * Created by Uladzislau_Parkhutsi on 11/17/2015.
 */
public class JavaFTPClient {

    private Object lock = new Object();
    private String host = null;
    private int security = 0;
    private boolean connected = false;

    //connect
    //download files
    //go into folders
    //go to parent dir
    //print dir content

    public String[] connect(String host) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException {
        short def;
        if(this.security == 1) {
            def = 990;
        } else {
            def = 21;
        }

        return this.connect(host, def);
    }

    public String[] connect(String host, int port) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException {
        Object var3 = this.lock;
        synchronized(this.lock) {
            if(this.connected) {
                throw new IllegalStateException("Client already connected to " + host + " on port " + port);
            } else {
                Socket connection = null;

                String[] var6;
                try {
                    connection = this.connector.connectForCommunicationChannel(host, port);
                    if(this.security == 1) {
                        connection = this.ssl(connection, host, port);
                    }

                    this.communication = new FTPCommunicationChannel(connection, this.pickCharset());
                    Iterator e = this.communicationListeners.iterator();

                    while(e.hasNext()) {
                        this.communication.addCommunicationListener((FTPCommunicationListener)e.next());
                    }

                    FTPReply e1 = this.communication.readFTPReply();
                    if(!e1.isSuccessCode()) {
                        throw new FTPException(e1);
                    }

                    this.connected = true;
                    this.authenticated = false;
                    this.parser = null;
                    this.host = host;
                    this.port = port;
                    this.username = null;
                    this.password = null;
                    this.utf8Supported = false;
                    this.restSupported = false;
                    this.mlsdSupported = false;
                    this.modezSupported = false;
                    this.dataChannelEncrypted = false;
                    var6 = e1.getMessages();
                } catch (IOException var17) {
                    throw var17;
                } finally {
                    if(!this.connected && connection != null) {
                        try {
                            connection.close();
                        } catch (Throwable var16) {
                            ;
                        }
                    }

                }

                return var6;
            }
        }
    }
}
