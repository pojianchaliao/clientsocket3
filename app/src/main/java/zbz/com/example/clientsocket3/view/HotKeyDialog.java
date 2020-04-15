package zbz.com.example.clientsocket3.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import zbz.com.example.clientsocket3.R;
import zbz.com.example.clientsocket3.app.FileActivity;
import zbz.com.example.clientsocket3.data.HotKeyData;
import zbz.com.example.clientsocket3.socket.CmdClientSocket;

public class HotKeyDialog extends AlertDialog {
    private Context context;
    private ArrayList<HotKeyData> hotKeyList;//热键列表，用于HotKeyGridAdapter填充数据
    private String title;//对话框的标题
    private CmdClientSocket cmdClientSocket;//用于HotKeyGridAdapter的视图点击触发cmdClientSocket向远程端发送命令
    private AlertDialog.Builder builder;

    public HotKeyDialog(Context context, final ArrayList<HotKeyData> hotKeyList, String title, final CmdClientSocket cmdClientSocket) {
        super(context);
        this.context = context;
        this.hotKeyList = hotKeyList;
        this.title = title;
        this.cmdClientSocket = cmdClientSocket;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        HotKeyGridAdapter adapter = new HotKeyGridAdapter(context, hotKeyList, cmdClientSocket);
        builder.setAdapter(adapter, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HotKeyData m1 = hotKeyList.get(which);
                cmdClientSocket.work("key:" + m1.getHotkeyCmd());
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ListView gridview = findViewById(R.id.gridvie);
//        HotKeyGridAdapter adapter = new HotKeyGridAdapter(context,hotKeyList,cmdClientSocket);
//        gridview.setAdapter(adapter);
//        setContentView(R.layout.gridview);
//    }

//
//    public void setTitle(String title) {
//        this.title = title;
//    }

}
