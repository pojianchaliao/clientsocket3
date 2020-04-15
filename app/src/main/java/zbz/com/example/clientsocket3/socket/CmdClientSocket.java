package zbz.com.example.clientsocket3.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class CmdClientSocket {
    private int msgType;
    public static final int SERVER_MSG_ERROR = 1;
    public static final int SERVER_MSG_OK = 0;
    private static Boolean isDebug;
    private String ip;
    private int port;
    private ArrayList<String> cmd;
    private int time_out = 10000;
    private int connect_timeout = 10000;
    private int transfer_timeout = 10000;
    private Handler handler;
    private Socket socket;
    public static final String KEY_SERVER_ACK_MSG = "KEY_SERVER_ACK_MSG";
    private OutputStreamWriter writer;
    private BufferedReader bufferedReader;

    public CmdClientSocket(String ip, int port, Handler handler) {
        this.port = port;
        this.ip = ip;
        this.handler = handler;
    }

    private void connect() throws IOException {
        InetSocketAddress address = new InetSocketAddress(ip, port);
        socket = new Socket();
        socket.connect(address, connect_timeout);
        if (!isDebug()) {
            socket.setSoTimeout(transfer_timeout);//设置传输数据的超时时间
        }
    }

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    private void writeCmd(String cmd) throws IOException {
        BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
        writer = new OutputStreamWriter(os, "UTF-8");
        writer.write("1\n");
        writer.write(cmd + "\n");
        writer.flush();
    }

    private ArrayList<String> readSocketMsg() throws IOException {
        ArrayList<String> msgList = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
        bufferedReader = new BufferedReader(isr);
        String numStr = bufferedReader.readLine();
        int lineNum = Integer.parseInt(numStr);
        if (lineNum < 1) {
            msgList.add("Receive empty message");
            return msgList;
        }
        String status = bufferedReader.readLine();
        if (status.equalsIgnoreCase("OK")) {
            msgType = SERVER_MSG_OK;
        } else {
            msgList.add(status);//将服务端的错误信息放入消息列表
        }
        for (int i = 1; i < lineNum; i++) {//反馈状态已读，故从1开始索引
            String str = bufferedReader.readLine();
            msgList.add(str);
        }
        return msgList;
    }

    private void close() throws IOException {
        bufferedReader.close();
        writer.close();
        socket.close();
    }

    private void doCmdTask(String cmd) {
        ArrayList<String> msgList = new ArrayList<>();
        try {
            connect();//连接服务端，若有异常，被捕捉
            writeCmd(cmd);//向服务端发送命令，未关闭输出流
            msgList = readSocketMsg();//读取socket输入流信息，并将结果存入msgList列表
            //若服务端返回信息的状态为"ok"，则将msgType设置为自定义常量
            //SERVER_MSG_OK（实际值为0）
            //服务端返回信息状态不是"ok"，则将msgType为SERVER_MSG_ERROR（实际值为1）
            close();//关闭Socket的输入流、输出流
        } catch (IOException e) {
            // TODO Auto-generated catch block
            msgType = SERVER_MSG_ERROR;//若捕捉到异常，则设置msgType为SERVER_MSG_ERROR（实际值为1）
            //SERVER_MSG_ERROR和SERVER_MSG_OK为自定义常量
            //public static int SERVER_MSG_OK=0;//用于发送给句柄的消息类型,放在消息的arg2中，表示服务端正常
            //public static int SERVER_MSG_ERROR=1;//表示服务端出错
            msgList.add(e.toString());//在msgList列表中放入错误信息
            e.printStackTrace();
        }
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY_SERVER_ACK_MSG, msgList);
        message.arg2 = msgType;
        //句柄bundle在handleMessage(Message msg)函数中首先对消息的arg2进行判断，若是SERVER_MSG_ERROR类型，则不更新列表，Toast显示错误信息
        //若message.arg2是SERVER_MSG_OK，则更新列表UI
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void work(final String cmd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doCmdTask(cmd);
            }
        }).start();
    }
}
