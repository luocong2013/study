package com.zync.swx.mysql;

import java.io.*;
import java.sql.*;

/**
 * @author LC
 * @version V1.0.0
 * @date 2018-1-30 16:49
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Test t = new Test();
        //t.test();
        t.select();
    }

    public void test() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/spider";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO mydata(img) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            FileInputStream in = readImage();
            ps.setBinaryStream(1, in, in.available());
            int count = ps.executeUpdate();
            if (count > 0) {
                System.out.println("插入成功");
            } else {
                System.err.println("插入失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void select() throws Exception {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/spider";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT img from mydata where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                InputStream inputStream = rs.getBinaryStream("img");
                readBin2Image(inputStream);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FileInputStream readImage() throws IOException {
        return new FileInputStream(new File("D:\\image\\74930690BE15480FA8EA7E8884AB9419.jpg"));
    }

    public void readBin2Image(InputStream inputStream) throws IOException {
        File file = new File("D:\\image\\123.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.flush();
        fos.close();
    }
}
