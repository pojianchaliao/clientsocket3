package zbz.com.example.clientsocket3.data;

import java.util.ArrayList;

public class PPT_HotKey {

    public ArrayList<HotKeyData> getHotkeyList() {
        ArrayList<HotKeyData> hotKeyDataArrayList = new ArrayList<>();
        hotKeyDataArrayList.add(new HotKeyData("切换程序", "VK_ALT+VK_TAB,VK_TAB"));
        hotKeyDataArrayList.add(new HotKeyData("选择当前程序", "VK_ALT,VK_ALT"));
        hotKeyDataArrayList.add(new HotKeyData("从头放映", "VK_F5"));
        hotKeyDataArrayList.add(new HotKeyData("从当前放映", "VK_SHIFT+VK_F5"));
        hotKeyDataArrayList.add(new HotKeyData("上一页", "VK_PAGE_UP"));
        hotKeyDataArrayList.add(new HotKeyData("下一页", "VK_PAGE_DOWN"));
        hotKeyDataArrayList.add(new HotKeyData("结束放映", "VK_ESCAPE"));
        hotKeyDataArrayList.add(new HotKeyData("退出程序", "VK_ALT+VK_F4"));
        return hotKeyDataArrayList;
    }
}
