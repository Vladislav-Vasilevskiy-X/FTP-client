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
    private final String RETURN = "return";

    private Scanner scanner = new Scanner(System.in);
    private JavaFTPClient client = new JavaFTPClient();

    public ConsoleUI() {
        run();
    }

    private void run() {
        while (true) {
            printMenu();
            String message = getMessageFromKeyboard();
            if (isCommand(message)) {
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
        System.out.println("Command: '" + RETURN + "' - will return you to main menu.");
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
            default:
                break;
        }
    }

    private void doConnect() {
        String tempString = "";
        while (true) {
            System.out.println("Type the host, which you would like to connect: ('return' - will return you to main menu)");
            tempString = getMessageFromKeyboard();

            if (isReturnMessage(tempString)) return;

            if (client.connectToHost(tempString) == JavaFTPClient.SUCCESS) {
                System.out.println("Connected to '" + tempString + "'");
                break;
            } else {
                System.out.println("Unable to connect '" + tempString + "'. Try again.");
            }
        }
    }

    private void doLogin() {
        String messageOne = "";
        String messageTwo = "";

        while (true) {
            System.out.println("Type the username: ('return' - will return you to main menu)");
            messageOne = getMessageFromKeyboard();

            if (isReturnMessage(messageOne)) return;

            System.out.println("Type the password: ('return' - will return you to main menu)");
            messageTwo = getMessageFromKeyboard();

            if (isReturnMessage(messageTwo)) return;

            int tempInt;
            if ((tempInt = client.logIn(messageOne, messageTwo)) == JavaFTPClient.SUCCESS) {
                System.out.println("Entered user: '" + messageOne + "' with password: '" + messageTwo + "'");
                break;
            } else {
                printErrorMessage(tempInt);
                //System.out.println("Unable to log in - user: '" + messageOne + "' with password: '" + messageTwo + "'");
            }
        }
    }

    private boolean isReturnMessage(String message) {
        if (message.equals(RETURN)) return true;
        else return false;
    }

    private void doDownload() {
        String tempString = "";
        while (true) {

            System.out.println("Type the file name (with extension) for downloading: ('return' - will return you to main menu)");
            tempString = getMessageFromKeyboard();

            if (isReturnMessage(tempString)) return;

            String filename = tempString;
            File file = new File(filename);
            int tempInt;
            if ((tempInt = client.downloadFile(tempString, file)) == JavaFTPClient.SUCCESS) {
                System.out.println("Downloaded '" + tempString + "' to '" + file.getAbsolutePath() + "'");
                break;
            } else {
                printErrorMessage(tempInt);
               // System.out.println("Can't download '" + tempString + "' to '" + file.getAbsolutePath() + "'");
            }

        }
    }

    private void doChangeDir() {
        String tempString = "";
        while (true) {

            System.out.println("Type the dirname: ('return' - will return you to main menu)");
            tempString = getMessageFromKeyboard();

            if (isReturnMessage(tempString)) return;
            int tempInt;
            if ((tempInt = client.changeDir(tempString)) == JavaFTPClient.SUCCESS) {
                System.out.println("Went to '" + tempString + "'");
                break;
            } else {
                printErrorMessage(tempInt);
               // System.out.println("Can't change current directory to '" + tempString + "'");
            }

        }

    }

    private void doBackToParent() {
        int tempInt;
        if ((tempInt = client.goToParentDirectory()) == JavaFTPClient.SUCCESS) {
            System.out.println("Went to parent dir: '" + client.getParentDir() + "'");
        } else {
            printErrorMessage(tempInt);
           // System.out.println("Can't change current directory to parent: '" + client.getParentDir() + "'");
        }
    }

    private void doPrint() {
        int tempInt;
        if ((tempInt = client.printContent()) == JavaFTPClient.SUCCESS)
            System.out.println("Content printed.");
        else printErrorMessage(tempInt);
    }

    private void printErrorMessage(int errorCode) {
        switch(errorCode) {
            case JavaFTPClient.NOT_LOGGED:
                System.out.println("You are not logged.");
                break;
            case JavaFTPClient.NOT_CONNECTED:
                System.out.println("You are not connected.");
                break;
            case JavaFTPClient.FAIL:
                System.out.println("Can't execute this operation.");
                break;
        }
    }

}
