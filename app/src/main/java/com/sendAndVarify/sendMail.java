package com.sendAndVarify;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendMail {
    URL obj;
    HttpURLConnection con;
    public final String address;
    public final String subject;
    public final String infomation;

    public sendMail(String address,String subject,String information){
        this.address = address;
        this.subject = subject;
        this.infomation = information;
    }

    public boolean send(){
        try{
            obj = new URL("http://10.0.2.2:8080/service/student-details");
            con = (HttpURLConnection) obj.openConnection();
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
                    "      <sch:MailRequest>\n" +
                    "         <sch:address>" +
                    address +
                    "</sch:address>\n" +
                    "         <sch:subject>" +
                    subject +
                    "</sch:subject>\n" +
                    "         <sch:information>" +
                    infomation +
                    "</sch:information>\n" +
                    "      </sch:MailRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>");
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            String responseBody = readResponseBody(con.getInputStream());
            String [] s = responseBody.split("<ns2:response>|</ns2:response>");
            if(s[1].equals("Y"))
                return true;
            else if(s[1].equals("N"))
                return false;

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
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
