package com.example.simon.androidlife;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Description:
 * <br>  练习网络编程相关的：I/O URL/URI
 * <p>
 * <br> Created by shixinzhang on 17/1/4.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class Test {

    public static final String BAIDU_URL = "http://www.baidu.com";
    public static final String IMG_URL = "http://upload-images.jianshu.io/upload_images/4943997-1f66b88dca374122.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";

    public static void main(String[] args) {

//        testInetAddress();
//        testNetworkInterface();
//        testProtocolSupport();

//        testUrlOpenStream();
//        testUrlOpenConnection();
//        testUrlGetContent();
        testUrlEquals();
    }

    /**
     * 比较两个 URL 是否相等
     */
    private static void testUrlEquals() {
        try {
            URL www = new URL("http://www.shixinzhang.top");
            URL url = new URL("http://shixinzhang.top");
            System.out.println(www + " equals to " + url + "? " + www.equals(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void testUrlGetContent() {
        try {
            URL url = new URL(IMG_URL);
            Object content = url.getContent();
            System.out.println("content type : " + content.getClass().getName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 URL 获得 socket 连接
     */
    private static void testUrlOpenConnection() {
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();   //读数据
            inputStream = new BufferedInputStream(inputStream);

            Reader r = new InputStreamReader(inputStream);
            int c;

            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }

            OutputStream outputStream = urlConnection.getOutputStream(); //还可以写数据
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 URL 获得输入流
     */
    private static void testUrlOpenStream() {
        InputStream in = null;
        try {
            URL url = new URL("http://www.baidu.com");
            in = url.openStream();
            //缓冲
            in = new BufferedInputStream(in);
            //改为字符流
            Reader r = new InputStreamReader(in);
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }


    /**
     * 测试当前虚拟机的协议支持情况
     */
    private static void testProtocolSupport() {
        //超文本传输协议
        printProtocol("http://www.shixinzhang.top");
        //安全的 http
        printProtocol("https://shixinzhang.top");
        //文本传输协议
        printProtocol("ftp://shixinzhang.top/java/xxx");
        //邮件传输协议
        printProtocol("mailto:sxzhang2016@163.com");
        //telnet
        printProtocol("telnet://xidian.edu");
        //本地文件访问
        printProtocol("file://etc/password");
        //gopher
        printProtocol("gopher://gopherxxx");
        //JAR
        printProtocol("jar:http://sdada.jar");
        //jdbc 的定制协议
        printProtocol("jdbc:mysql://lunaxx");
        //NFS 网络文件系统
        printProtocol("nfs://xxx");
    }

    private static void printProtocol(@NonNull final String urlString) {
        try {
            URL url = new URL(urlString);
            System.out.println(url.getProtocol() + " is supported!");
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            String protocol = urlString.substring(0, urlString.indexOf(":"));
            System.out.println(protocol + " is not supported!");
        }
    }

    private static void testNetworkInterface() {
        try {
            //UNIX 系统上以太网接口名为 eth0
            //Windows 名字类似 CE31 和 ELX100，具体名称取决于网络接口的厂商名和硬件模型名
            NetworkInterface ni = NetworkInterface.getByName("eth0");
            if (ni == null) {
                System.out.println("No such interface: eth0");
            }

            //返回与指定 IP 绑定的网络接口
            NetworkInterface networkInterface1 = NetworkInterface.getByInetAddress(
                    InetAddress.getByName("127.0.0.1"));
            if (networkInterface1 != null) {
                System.out.println("getByInetAddress：" + networkInterface1);
            }

            //本地回送地址类似： lo

            //列出本地主机上的所有网络接口，联网和不联网的显示结果不同
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                System.out.println(networkInterface);

                //输出这个 NetworkInterface 下的所有 IP 信息
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    System.out.println(inetAddress);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void testInetAddress() {

        InetAddress machine = null;
        try {
            machine = InetAddress.getLocalHost();
            printAddress(machine);


            System.out.println("***************");

            InetAddress[] addresses = InetAddress.getAllByName("shixinzhang.top");
            for (InetAddress address : addresses) {
                printAddress(address);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void printAddress(final InetAddress machine) {
        if (machine == null) return;

        //getter methods
        System.out.println(machine.getHostName());
        System.out.println(machine.getHostAddress());
        System.out.println(Arrays.toString(machine.getAddress()));
        System.out.println(machine.getCanonicalHostName());
        System.out.println();

        //address type
        //通配地址，匹配系统中的任何地址，IPv4 中是四个 0, [0,0,0,0]; IPv6 是八个0
        System.out.println(machine.isAnyLocalAddress());
        //回送地址，直接在 IP 层连接计算机，不使用任何物理硬件.可以绕过以太网、PPP 和其他驱动程序，IPv4 中是 [127,0,0,1]
        System.out.println(machine.isLoopbackAddress());
        //IPv6 本地链接地址，帮助 IPv6 实现自配置，与 Ipv4 的 DHCP 非常相似,但不使用服务器
        System.out.println(machine.isLinkLocalAddress());
        //IPv6 本地网站地址，与本地链接地址，但它可以由路由器在内网转发
        System.out.println(machine.isSiteLocalAddress());
        //组播地址，组播会将内容广播给所有预定的计算机
        System.out.println(machine.isMulticastAddress());
        //全球组播地址，
        System.out.println(machine.isMCGlobal());
    }

    public static void read(InputStream in) throws IOException {
        byte[] bytes = new byte[1024];
        for (int i = 0; i < bytes.length; i++) {
            int b = in.read();
            if (b == -1) break;
            bytes[i] = (byte) b;
        }
    }

    public static void readBuffered(InputStream in) throws IOException {
        int bytesRead = 0;
        int bytesToRead = 1024;

        byte[] input = new byte[bytesToRead];
        while (bytesRead < bytesToRead) {
            int result = in.read(input, bytesRead, bytesToRead - bytesRead);
            if (result == -1) break;    //流结束
            bytesRead += result;
        }
    }

    public static String readBufferedReader(InputStream in) throws IOException {
        Reader r = new InputStreamReader(in, "UTF-8");
        r = new BufferedReader(r, 1024);
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = r.read()) != -1) {
            sb.append((char) c);
        }
        return sb.toString();
    }
}
