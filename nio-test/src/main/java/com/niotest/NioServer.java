package com.niotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @User: jufeng
 * @Date: 18-1-21
 * @Time: 下午8:10
 **/
public class NioServer {
    private Selector selector;

    private void init(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        // register event to selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public void listener() throws IOException {
        System.out.println("server is starting listener...");
        while (true){
            //当注册的事件到达时，方法返回；否则,该方法会一直阻塞
            selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()){
               SelectionKey selectionKey = iterator.next();
               iterator.remove();//delete current key

                // if client request connect event
               if (selectionKey.isAcceptable()){
                   ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                   // 获得和客户端链接通道
                   SocketChannel socketChannel = serverSocketChannel.accept();
                   socketChannel.configureBlocking(false);
                   //write to client data
                   socketChannel.write(ByteBuffer.wrap(new String("im server").getBytes()));
                   //在和客户端链接成功后,为了可以接受client的数据,现在可读时间
                   socketChannel.register(selector,SelectionKey.OP_READ);
               }else if (selectionKey.isReadable()){
                   SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                   ByteBuffer buffer = ByteBuffer.allocate(1024);
                   buffer.clear();
                   socketChannel.read(buffer);
                   byte [] data = buffer.array();
                   String s = new String(data);
                   System.out.println("read data from client : "+s.trim());
                   socketChannel.write(ByteBuffer.wrap(new String("read success").getBytes()));
               }
            }
        }
    }


    public static void main(String [] args){
        NioServer server = new NioServer();
        try {
            server.init(8890);
            server.listener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
