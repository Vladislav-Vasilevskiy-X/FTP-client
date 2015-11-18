package ui;

import client.JavaFTPClient;

import java.io.File;
import java.util.Scanner;

/**
 * Created by Uladzislau_Parkhutsi on 11/18/2015.
 */
public class ConsoleUI {

    private final String CONNECT = "connect";
    private final String LOGIN = "login";
    private final String DOWNLOAD = "download";
    private final String CHANGE_DIR = "changedir";
    private final String GO_PARENT = "goparent";
    private final String PRINT = "print";
    private final String EXIT = "exit";

    Scanner scanner = new Scanner(System.in);
    JavaFTPClient client = new JavaFTPClient();

    public ConsoleUI() {
        run();
    }

    private void run() {
        while (true) {

            printMenu();
            String message = getMessageFromKeyboard();
            if(isCommand(message)) {
                switchCommand(message);
            }

        }
    }

    private String getMessageFromKeyboard() {
        return scanner.nextLine();
    }

    private void printMenu() {
        System.out.println("Command: '" + CONNECT + "' - connect to ftp server.");
        System.out.println("Command: '" + LOGIN + "' - login to ftp server.");
        System.out.println("Command: '" + DOWNLOAD + "' - download file by it's name(with ext.).");
        System.out.println("Command: '" + CHANGE_DIR + "' - go into another directory.");
        System.out.println("Command: '" + GO_PARENT + "' - go back to parent directory.");
        System.out.println("Command: '" + PRINT + "' - print names of files in current folder.");
        System.out.println("Command: '" + EXIT + "' - no comments).");
        System.out.println("Enter the command:");
    }

    private boolean isCommand(String message) {
        if (message.equals(CONNECT)
                || message.equals(DOWNLOAD)
                || message.equals(CHANGE_DIR)
                || message.equals(GO_PARENT)
                || message.equals(EXIT)
                || message.equals(LOGIN)
                || message.equals(PRINT)) {
            return true;
        } else return false;
    }

    private void switchCommand(String command) {
        switch (command) {
            case CONNECT:
                doConnect();
                break;
            case LOGIN:
                doLogin();
                break;
            case DOWNLOAD:
                doDownload();
                break;
            case CHANGE_DIR:
                doChangeDir();
                break;
            case GO_PARENT:
                doBackToParent();
                break;
            case PRINT:
                doPrint();
                break;
            case EXIT:
                System.exit(-1);
            default: break;
        }
    }

    private void doConnect() {
        String tmp = "";
        while (true) {
            System.out.println("Type the host, which you would like to connect: ");

            tmp = getMessageFromKeyboard();

            if(client.connectToHost(tmp) == JavaFTPClient.SUCCESS) {
                System.out.println("Connected to '" + tmp + "'");
                break;
            } else {
                System.out.println("Unable to connect '" + tmp + "'. Try again.");
            }
        }
    }

    private void doLogin() {
        String user = "";
        String pass = "";

        while (true) {
            System.out.println("Type the username: ");
            user = getMessageFromKeyboard();

            System.out.println("Type the password: ");
            pass = getMessageFromKeyboard();

            if(client.logIn(user, pass) == JavaFTPClient.SUCCESS) {
                System.out.println("Entered user: '" + user + "' with password: '" + pass + "'");
                break;
            } else {
                System.out.println("Unable to log in - user: '" + user + "' with password: '" + pass + "'");
            }
        }
    }

    private void doDownload(){
        String tmp = "";
        while (true) {

            System.out.println("Type the file name (with extension) for downloading: ");
            tmp = getMessageFromKeyboard();

            String filename = tmp;
            File file = new File(filename);

            if(client.downloadFile(tmp, file) == JavaFTPClient.SUCCESS) {
                System.out.println("Downloaded '" + tmp + "' to '" + file.getAbsolutePath() + "'");
                break;
            } else {
                System.out.println("Can't download '" + tmp + "' to '" + file.getAbsolutePath() + "'");
            }

        }
    }

    private void doChangeDir(){
        String tmp = "";
        while (true) {

            System.out.println("Type the dirname: ");
            tmp = getMessageFromKeyboard();

            if(client.changeDir(tmp) == JavaFTPClient.SUCCESS) {
                System.out.println("Went to '" + tmp + "'");
                break;
            } else {
                System.out.println("Can't change current directory to '" + tmp + "'");
            }

        }

    }
    private void doBackToParent(){
        if(client.goToParentDirectory() == JavaFTPClient.SUCCESS) {
            System.out.println("Went to parent dir: '" + client.getParentDir() + "'");
        } else {
            System.out.println("Can't change current directory to parent: '" + client.getParentDir() + "'");
        }
    }

    private void doPrint(){
        client.printContent();
        System.out.println("Content printed.");
    }


}
