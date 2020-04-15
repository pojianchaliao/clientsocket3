package zbz.com.example.clientsocket3.data;

import java.util.ArrayList;

public class Default_HotKey {
    public ArrayList<HotKeyData> getHotkeyList() {
        ArrayList<HotKeyData> hotKeyDataArrayList = new ArrayList<>();
        hotKeyDataArrayList.add(new HotKeyData("最大化", "VK_ALT+VK_SPACE+VK_X,VK_SPACE+VK_ALT+VK_X"));
        hotKeyDataArrayList.add(new HotKeyData("还原", "VK_ALT+VK_SPACE+VK_R"));
        hotKeyDataArrayList.add(new HotKeyData("切换程序", "VK_ALT+VK_TAB,VK_TAB"));
        hotKeyDataArrayList.add(new HotKeyData("选择当前程序", "VK_ALT,VK_ALT"));
        hotKeyDataArrayList.add(new HotKeyData("退出程序", "VK_ALT+VK_F4"));
        return hotKeyDataArrayList;
    }
}
