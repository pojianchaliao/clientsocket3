package zbz.com.example.clientsocket3.operator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import zbz.com.example.clientsocket3.data.NetFileData;
import zbz.com.example.clientsocket3.view.NetFileDataAdapter;

import static zbz.com.example.clientsocket3.socket.CmdClientSocket.KEY_SERVER_ACK_MSG;
import static zbz.com.example.clientsocket3.socket.CmdClientSocket.SERVER_MSG_ERROR;
import static zbz.com.example.clientsocket3.socket.CmdClientSocket.KEY_SERVER_ACK_MSG;
import static zbz.com.example.clientsocket3.socket.CmdClientSocket.SERVER_MSG_ERROR;

public class ShowRemoteFileHandler extends Handler {
    NetFileDataAdapter adapter;
    private Context context;
    private ListView listView;

    public ShowRemoteFileHandler(Context context, ListView listVie) {
        super();
        this.context = context;
        this.listView = listVie;
    }

    public void handleMessage(Message msg) {
        Bundle b = msg.getData();
        ArrayList<String> msgList = (ArrayList<String>) b.get(KEY_SERVER_ACK_MSG);
        ArrayList newList = new ArrayList();
        if (msg.arg2 == SERVER_MSG_ERROR) {
            String error = String.valueOf(msgList.get(0));
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            return;
        } else {
            for (int i = 0; i < 1; i++) {
                NetFileData newdata0 = new NetFileData(msgList.get(i), msgList.get(0));
                newdata0.setFileName(String.valueOf(msgList.get(i).split(">")[0]));
                newList.add(newdata0);
            }
            for (int i = 1; i < msgList.size(); i++) {
                NetFileData newdata = new NetFileData(msgList.get(i), msgList.get(0));
                newdata.setFileName(String.valueOf(msgList.get(i).split(">")[0]));
//                newdata.setFilePath(msgList.get(0));
                newdata.setFileSize(Long.valueOf(msgList.get(i).split(">")[2]));
                newdata.setFileSizeStr(FormetFileSize(newdata.getFileSize()));
                newdata.setFileModifiedDate(String.valueOf(msgList.get(i).split(">")[1]));
                newdata.setFileType(Integer.valueOf(msgList.get(i).split(">")[3]));
                newList.add(newdata);
            }
            updateListView(newList);
        }
    }

    private String FormetFileSize(long file) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (file < 1024) {
            fileSizeString = df.format((double) file) + "B";
        } else if (file < 1048576) {
            fileSizeString = df.format((double) file / 1024) + "K";
        } else if (file < 1073741824) {
            fileSizeString = df.format((double) file / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) file / 1073741824) + "G";
        }
        return fileSizeString;
    }

    private void updateListView(ArrayList<NetFileData> list) {
        adapter = new NetFileDataAdapter(context, list);
        listView.setAdapter(adapter);
    }

}
