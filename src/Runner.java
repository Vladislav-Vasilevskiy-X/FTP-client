import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Uladzislau_Parkhutsi on 11/17/2015.
 */
public class Runner {
    public static void main(String[] args) {

        FTPClient ftpClient = new FTPClient();
        String fileName = "100KB.zip";
        File downloadedFile = new File(fileName);

        try {
            ftpClient.connect("speedtest.tele2.net");
            ftpClient.login("anonymous", "ftp4j");
            ftpClient.download(fileName, downloadedFile);
        } catch (IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException | FTPAbortedException e) {
            e.printStackTrace();
        }
    }
}
