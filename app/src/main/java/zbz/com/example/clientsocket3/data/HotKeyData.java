package zbz.com.example.clientsocket3.data;

public class HotKeyData {
    private String hotkeyName = "";
    private String hotkeyCmd = "";

    public HotKeyData(String hotkeyName, String hotkeyCmd) {
        this.hotkeyCmd = hotkeyCmd;
        this.hotkeyName = hotkeyName;
    }

    public String getHotkeyName() {
        return hotkeyName;
    }

    public void setHotkeyName(String hotkeyName) {
        this.hotkeyName = hotkeyName;
    }

    public String getHotkeyCmd() {
        return hotkeyCmd;
    }

    public void setHotkeyCmd(String hotkeyCmd) {
        this.hotkeyCmd = hotkeyCmd;
    }
}
