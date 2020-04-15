package zbz.com.example.clientsocket3.data;

public class NetFileData {
    private String fileInfo = ">>>>";
    private long fileSize = 0;// 文件长度应该long型数据，否则大于2GB的文件大小无法表达
    private String fileName = "$error";// 文件名称，不含目录信息,默认值用于表示文件出错
    private String filePath = ".\\";// 该文件对象所处的目录，默认值为当前相对目录
    private String fileSizeStr = "0";// 文件的大小，用字符串表示，能智能地选择B、KB、MB、GB来表达
    private int fileType = 0;// fileType=0为文件，fileType=1为普通文件夹，fileType=2为盘符
    private String fileModifiedDate = "1970-01-01 00:00:00";// 文件最近修改日期，默认值为1970年基准时间


    public String getFilePath() {
        return filePath;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public void setFileModifiedDate(String fileModifiedDate) {
        this.fileModifiedDate = fileModifiedDate;
    }

    public NetFileData(String fileInfo, String filePath) {
        this.filePath = filePath;
        this.fileInfo = fileInfo;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }


    public int getFileType() {
        fileType = Integer.valueOf(fileInfo.split(">")[3]);
        return fileType;
    }

    public String getFileModifiedDate() {

        return fileModifiedDate;
    }

}
