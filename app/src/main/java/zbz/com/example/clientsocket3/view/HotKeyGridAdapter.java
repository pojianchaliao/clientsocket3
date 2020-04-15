package zbz.com.example.clientsocket3.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import zbz.com.example.clientsocket3.R;
import zbz.com.example.clientsocket3.data.HotKeyData;
import zbz.com.example.clientsocket3.socket.CmdClientSocket;

public class HotKeyGridAdapter extends ArrayAdapter {
    Context context;
    List<HotKeyData> list;
    CmdClientSocket cmdClientSocket;
    HotKeyData m1;

    public HotKeyGridAdapter(Context context, List<HotKeyData> list, CmdClientSocket cmdClientSocket) {
        super(context, android.R.layout.simple_list_item_1, list);
        this.context = context;
        this.list = list;
        this.cmdClientSocket = cmdClientSocket;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.hotkey_row_view, null, false);
        } else {
            v = convertView;
        }
        m1 = list.get(position);
        System.out.println("1111    " + position);
        System.out.println("2222    " + m1.getHotkeyCmd());
        System.out.println("!    " + m1.getHotkeyName());
        TextView tv = v.findViewById(R.id.tv1);
        tv.setText(m1.getHotkeyName());
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cmdClientSocket.work("key:"+m1.getHotkeyCmd());
//            }
//        });
        return v;
    }

//    @Override
//    public void onClick(View v) {
//        cmdClientSocket.work("key:"+m1.getHotkeyCmd());
//    }
}
