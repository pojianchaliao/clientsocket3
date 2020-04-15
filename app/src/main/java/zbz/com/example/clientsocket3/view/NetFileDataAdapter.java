package zbz.com.example.clientsocket3.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import zbz.com.example.clientsocket3.R;
import zbz.com.example.clientsocket3.data.NetFileData;
import zbz.com.example.clientsocket3.data.NetFileData;
import zbz.com.example.clientsocket3.data.NetFileData;

public class NetFileDataAdapter extends ArrayAdapter {
    private int[] mIcons;
    private Context context;

    ArrayList<NetFileData> msgList;

    public NetFileDataAdapter(@NonNull Context context, ArrayList<NetFileData> msgList) {
        super(context, android.R.layout.simple_list_item_1, msgList);
        this.msgList = msgList;
        this.context = context;
        mIcons = new int[]{R.drawable.folder, R.drawable.other, R.drawable.driver};
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.file_row_view, null, false);
        } else {
            v = convertView;
        }
        ImageView iv = v.findViewById(R.id.iv_folder);
        TextView tv = v.findViewById(R.id.tv_foldlist);
        iv.setVisibility(View.GONE);
        NetFileData m1 = msgList.get(position);
        if (position < 1) {  //路径   后面加上 。。。 和。。
            tv.setText("");
            tv.setText(m1.getFileName());
        }
        if (position >= 1 && position < msgList.size()) {
            tv.setText(m1.getFileName() + "     " + m1.getFileSizeStr() + "     " + m1.getFileModifiedDate());
            switch (m1.getFileType()) {
                case 0:
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageResource(mIcons[1]);
                    break;
                case 1:
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageResource(mIcons[0]);
                    break;
                case 2:
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageResource(mIcons[3]);
                    break;
            }
        }
        return v;

    }
}
