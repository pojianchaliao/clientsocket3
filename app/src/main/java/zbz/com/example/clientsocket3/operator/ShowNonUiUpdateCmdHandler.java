package zbz.com.example.clientsocket3.operator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;

import static zbz.com.example.clientsocket3.socket.CmdClientSocket.KEY_SERVER_ACK_MSG;
import static zbz.com.example.clientsocket3.socket.CmdClientSocket.SERVER_MSG_ERROR;
import static zbz.com.example.clientsocket3.socket.CmdClientSocket.KEY_SERVER_ACK_MSG;
import static zbz.com.example.clientsocket3.socket.CmdClientSocket.SERVER_MSG_ERROR;

public class ShowNonUiUpdateCmdHandler extends Handler {
    private Context context;

    public ShowNonUiUpdateCmdHandler(Context context) {
        super();
        this.context = context;
    }

    public void handleMessage(Message msg) {
        //服务端返回arraylist 类型 但只有一组数据 然后获取后 toast显示
        Bundle b = msg.getData();
        ArrayList<String> msgList = (ArrayList<String>) b.get(KEY_SERVER_ACK_MSG);
        if (msg.arg2 == SERVER_MSG_ERROR) {
            String error = String.valueOf(msgList.get(0));
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }
    }
}