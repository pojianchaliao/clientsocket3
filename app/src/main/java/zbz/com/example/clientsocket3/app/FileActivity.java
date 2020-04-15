package zbz.com.example.clientsocket3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import zbz.com.example.clientsocket3.R;
import zbz.com.example.clientsocket3.data.CmdServerIpSetting;
import zbz.com.example.clientsocket3.data.NetFileData;
import zbz.com.example.clientsocket3.operator.HotKeyGenerator;
import zbz.com.example.clientsocket3.operator.ShowNonUiUpdateCmdHandler;
import zbz.com.example.clientsocket3.operator.ShowRemoteFileHandler;
import zbz.com.example.clientsocket3.socket.CmdClientSocket;
import zbz.com.example.clientsocket3.view.HotKeyDialog;

import static zbz.com.example.clientsocket3.data.CmdServerIpSetting.ip;

public class FileActivity extends AppCompatActivity {

    ListView lv_folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_main);
        lv_folder = findViewById(R.id.lv_folder);
        Button bt_sub = findViewById(R.id.bt_submit);
//        final EditText et_ip = findViewById(R.id.et_ip);
        final TextView et_cmd = findViewById(R.id.et_cmd);
//        final EditText et_port =findViewById(R.id.et_port);
        final ShowRemoteFileHandler myhander = new ShowRemoteFileHandler(this, lv_folder);
        registerForContextMenu(lv_folder);
        bt_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cmd = et_cmd.getText().toString();
//                final String ip = String.valueOf(et_ip.getText()).trim();
//                final int port = Integer.valueOf(String.valueOf(et_port.getText()));
                CmdClientSocket socketClient = new CmdClientSocket(ip, CmdServerIpSetting.port, myhander);
                socketClient.work(cmd);
            }
        });
        lv_folder.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                NetFileData fileData = (NetFileData) arg0.getItemAtPosition(arg2);
                String pwd = fileData.getFilePath();
                System.out.println(pwd);
                String filePath = "";
                if (pwd.endsWith("/") | pwd.endsWith("\\")) {
                    //文件路径可能带"/"结尾，例如"c://aaa/b/"也可能是"c://aaa/b"因此需要考虑周全
                    //另外Windows系统和Linux系统文件夹分隔符不同，对有些系统其文件目录的表示是"c:\\\\aaa\\b\\"，注意"\\"转义成"\"
                    filePath = pwd + fileData.getFileName();
                } else {
                    filePath = pwd + File.separator + fileData.getFileName();
                }
                if (fileData.getFileType() >= 1) {
                    if (fileData.getFileName().equals("...")) {
                        //处理根目录，列出所有盘符
                        filePath = "...";
                    }
                    if (fileData.getFileName().equals("..")) {
                        //处理根目录，列出所有盘符
                        filePath = "..";
                    }
                    //数据从服务器返回时就有了... 和 ..
                    ShowRemoteFileHandler showRemoteFileHandler = new ShowRemoteFileHandler(FileActivity.this, lv_folder);//会更新ListView的句柄
                    CmdClientSocket cmdClientSocket = new CmdClientSocket(CmdServerIpSetting.ip, CmdServerIpSetting.port, showRemoteFileHandler);
                    cmdClientSocket.work("dir:" + filePath);

                } else {
                    ShowNonUiUpdateCmdHandler showNonUiUpdateCmdHandler = new ShowNonUiUpdateCmdHandler(FileActivity.this);//直接Toast显示的句柄，不更新ListView
                    CmdClientSocket cmdClientSocket = new CmdClientSocket(CmdServerIpSetting.ip, CmdServerIpSetting.port, showNonUiUpdateCmdHandler);
                    cmdClientSocket.work("opn:" + filePath);
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.file_list_context_menu, menu);//R.menu.file_list_context_menu为上下文菜单
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = contextMenuInfo.position;
        NetFileData netFileData = (NetFileData) lv_folder.getAdapter().getItem(pos);//其中listView为显示文件列表的视图
        switch (item.getItemId()) {
            case R.id.item1:// 弹出热键对话框
                showHotKeyDialog(netFileData);//能根据netFileData类型决定弹出相应的热键对话框
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void showHotKeyDialog(NetFileData netFileData) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("热键操作表")
        ShowNonUiUpdateCmdHandler showNonUiUpdateCmdHandler = new ShowNonUiUpdateCmdHandler(FileActivity.this);
        CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, CmdServerIpSetting.port, showNonUiUpdateCmdHandler);
        //showNonUiUpdateCmdHandler句柄，处理socket的接收信息，若远程服务端正确执行命令，不做任何UI更新；若远程服务端出错，Toast显示出错信息
        new HotKeyDialog(FileActivity.this, HotKeyGenerator.getHotkeyList(netFileData), "热键操作表", cmdClientSocket);
        //HotKeyDialog的构造函数为：public HotKeyDialog(Context context,ArrayList<HotKeyData> hotKeyList,String title,CmdClientSocket cmdClientSocket)
        //其中Context context为上下文
        //ArrayList<HotKeyData> hotKeyList,传入的热键列表，热键对象HotKeyData为自定义类，具有热键的名称以及热键对应的操作
        //例如HotKeyData("退出程序", "key:vk_alt+vk_f4")则构造了一个名称为"退出程序"，通过命令"key:vk_alt+vk_f4"实现alt+f4的热键操作
        //HotKeyGenerator.getHotkeyList(netFileData)为静态方法
        //public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData),根据fileData类型决定产生什么样的热键数据
    }
}
