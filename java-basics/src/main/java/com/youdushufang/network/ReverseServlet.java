package com.youdushufang.network;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet("/ReverseServlet")
public class ReverseServlet extends HttpServlet {

    private static final String MESSAGE = "Error during Servlet processing";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html; charset=utf-8");
        out.println("Hello, world!");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int len = req.getContentLength();
            byte[] input = new byte[len];

            ServletInputStream sin = req.getInputStream();
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length - count)) != -1) {
                count += c;
            }
            sin.close();

            String inString = new String(input);
            int index = inString.indexOf("=");
            if (index == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(MESSAGE);
                resp.getWriter().close();
                return;
            }
            String value = inString.substring(index + 1);

            // decode application/x-www-form-urlencoded string
            String decodedString = URLDecoder.decode(value, "UTF-8");

            // reverse the String
            String reverseStr = (new StringBuffer(decodedString)).reverse().toString();

            // set the response code and write the response data
            resp.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream());

            writer.write(reverseStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            try{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(e.getMessage());
                resp.getWriter().close();
            } catch (IOException ignored) { }
        }

    }
}
