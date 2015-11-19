package client;

import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Uladzislau_Parkhutsi on 11/17/2015.
 */
public class JavaFTPClient extends FTPClient {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NOT_CONNECTED = 2;
    public static final int NOT_LOGGED = 3;

    private FTPClient ftpClient = new FTPClient();
    private String parentDir = "";

    public JavaFTPClient() {
    }

    public int connectToHost(String host) {
        try {
            ftpClient.connect(host);
            return SUCCESS;
        } catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException e) {
            return FAIL;
        }
    }

    public int logIn(String username, String password) {
        if (ftpClient.isConnected())
            try {
                ftpClient.login(username, password);
                return SUCCESS;
            } catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException e) {
                return FAIL;
            }
        else return NOT_CONNECTED;
    }

    public int downloadFile(String remoteFileName, File localFile) {
        if (ftpClient.isAuthenticated())
            try {
                ftpClient.download(remoteFileName, localFile);
                return SUCCESS;
            } catch (IOException | FTPException | FTPIllegalReplyException | FTPDataTransferException | FTPAbortedException e) {
                return FAIL;
            }
        else return NOT_LOGGED;
    }

    public int changeDir(String path) {
        if (ftpClient.isAuthenticated())
            try {
                parentDir = ftpClient.currentDirectory();
                ftpClient.changeDirectory(path);
                return SUCCESS;
            } catch (IOException | FTPException | FTPIllegalReplyException e) {
                return FAIL;
            }
        else return NOT_LOGGED;
    }

    public int goToParentDirectory() {
        if (ftpClient.isAuthenticated()) {
            String tmp = parentDir;
            try {
                parentDir = ftpClient.currentDirectory();
                ftpClient.changeDirectory(tmp);
                return SUCCESS;
            } catch (IOException | FTPException | FTPIllegalReplyException e) {
                return FAIL;
            }
        } else return NOT_LOGGED;
    }

    public int printContent() {
        if (ftpClient.isAuthenticated()) {
            try {
                for (String name : ftpClient.listNames()) {
                    System.out.print("\n" + name);
                }
                return SUCCESS;
            } catch (IOException | FTPException | FTPIllegalReplyException | FTPDataTransferException
                    | FTPAbortedException | FTPListParseException e) {
                return FAIL;
            }
        } else return NOT_LOGGED;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }

    public String getParentDir() {
        return parentDir;
    }
}
