package com.sendAndVarify;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class varifyAddrClass {
    public URL obj;
    public HttpURLConnection con;
    public final String addr;


    public varifyAddrClass(String addr){
        this.addr = addr;
    }

    public boolean varifyEmail(){
        try{
            obj = new URL("http://10.0.2.2:8080/service/student-details");
            con = (HttpURLConnection) obj.openConnection();
            System.out.println("连接成功");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", " text/xml; charset=utf-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" \n" +
                    "\txmlns:sch=\"http://www.howtodoinjava.com/xml/school\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <sch:VarifyRequest>\n" +
                    "         <sch:address>" +
                    addr +
                    "</sch:address>\n" +
                    "      </sch:VarifyRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>");
            wr.flush();
            wr.close();
            System.out.println("发送成功");
            int responseCode = con.getResponseCode();
            String responseBody = readResponseBody(con.getInputStream());
            String [] s = responseBody.split("<ns2:response>|</ns2:response>");
            System.out.println(s[1]);
            if (s[1].equals("Y"))
                return true;
            return false;

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    private String readResponseBody(InputStream inputStream)throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader((inputStream)));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine())!= null){
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
