package com.niotest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

/**
 * @User: jufeng
 * @Date: 18-1-21
 * @Time: 下午8:04
 **/
public class FileChannelTest {

    public static void main(String [] args){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("");
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int byteRead = channel.read(buffer);
            while (byteRead!=1){

                buffer.flip();
                buffer.get();
                while(buffer.hasRemaining()){
                    System.out.print((char) buffer.get()); // read 1 byte at a time
                }
                buffer.clear(); //make buffer ready for writing
                byteRead = channel.read(buffer);


            }

            buffer.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //
            try {
                if (fileInputStream!=null)
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
