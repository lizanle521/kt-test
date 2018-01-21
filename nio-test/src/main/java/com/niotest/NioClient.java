package com.niotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @User: jufeng
 * @Date: 18-1-21
 * @Time: 下午8:36
 **/
public class NioClient {
    private Selector selector;

    private void init(String ip ,int port) throws IOException {
        SocketChannel socketChannel =SocketChannel.open();
        socketChannel.configureBlocking(false);
        this.selector = Selector.open();
        socketChannel.connect(new InetSocketAddress(ip,port));

        // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
        //用channel.finishConnect();才能完成连接
        socketChannel.register(selector, SelectionKey.OP_CONNECT);


    }

    private void listener() throws IOException {
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.keys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
              //  iterator.remove();
                if (key.isConnectable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 如果正在连接，则完成连接
                    if (socketChannel.isConnectionPending()){
                        socketChannel.finishConnect();
                    }

                    socketChannel.configureBlocking(false);
                    socketChannel.write(ByteBuffer.wrap(new  String("i from client").getBytes()));
                    socketChannel.register(selector,SelectionKey.OP_READ);

                }else if (key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.clear();
                    socketChannel.read(buffer);
                    byte [] data = buffer.array();
                    String s = new String(data);
                    System.out.println("read data from server : "+s.trim());
                   // socketChannel.write(ByteBuffer.wrap(new String("read success").getBytes()));
                }


            }
        }
    }

    public static void main(String [] as){
        NioClient client = new NioClient();
        try {
            client.init("127.0.0.1",8890);
            client.listener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
