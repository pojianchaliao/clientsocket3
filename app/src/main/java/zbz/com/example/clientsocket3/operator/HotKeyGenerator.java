package zbz.com.example.clientsocket3.operator;

import java.util.ArrayList;

import zbz.com.example.clientsocket3.data.Default_HotKey;
import zbz.com.example.clientsocket3.data.HotKeyData;
import zbz.com.example.clientsocket3.data.Movie_HotKey;
import zbz.com.example.clientsocket3.data.NetFileData;
import zbz.com.example.clientsocket3.data.PPT_HotKey;

public class HotKeyGenerator {
    public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData) {
        ArrayList<HotKeyData> hotKeyDataArrayList = new ArrayList<>();
        String filename = fileData.getFileName();
        String[] fileNameSuffix = filename.split("\\.");
        System.out.println("!  " + filename);
        for (int i = 0; i < fileNameSuffix.length; i++)
            System.out.println(fileNameSuffix[i]);

        switch (fileNameSuffix[1]) {
            case "pptx":
                hotKeyDataArrayList = new PPT_HotKey().getHotkeyList();
                break;
            case "mp4":
                hotKeyDataArrayList = new Movie_HotKey().getHotkeyList();
                break;
            default:
                hotKeyDataArrayList = new Default_HotKey().getHotkeyList();
                break;
        }
        return hotKeyDataArrayList;
    }
}
