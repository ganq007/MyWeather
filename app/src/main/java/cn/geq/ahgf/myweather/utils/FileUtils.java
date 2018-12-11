package cn.geq.ahgf.myweather.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String getFileString(File file){
        StringBuilder builder = new StringBuilder();
        try {
            FileReader in = new FileReader(file);
            char [] end = new char[1024];
            int i = 0;
            while((i=in.read(end))!=-1){
                int read = in.read(end);
                builder.append(end);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    public static  String getJson(File file) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
            //创建一个使用默认大小输入缓冲区的缓冲字符输入流。
            BufferedReader br=new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }

    public static  String getallJson(InputStream inputStream) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader reader=new InputStreamReader(inputStream);
            //创建一个使用默认大小输入缓冲区的缓冲字符输入流。
            BufferedReader br=new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }







}
