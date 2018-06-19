package cn.khthink.easyapi.api;

import cn.khthink.easyapi.config.CoreConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/*
    Create by KH at 2017/10/25 13:19 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

/**
 * api访问
 *
 * @author kh
 */
@WebServlet({"/api", "/res/*", "/layui/*","/lay/*"})
public class Api extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CoreConfig.charset);
        resp.setCharacterEncoding(CoreConfig.charset);
        InputStream is;
        String uri = req.getRequestURI();
        String[] uriinfo = uri.split("\\.");
        if (uriinfo.length == 1) {
            uriinfo[0] = "html";
            is = this.getClass().getResourceAsStream("index.html");
        } else {
            is = this.getClass().getResourceAsStream(uri.replaceFirst("/", "").replaceAll("%20", " "));
        }
        switch (uriinfo[uriinfo.length - 1]) {
            case "js":
                resp.setContentType("application/x-javascript;charset=UTF-8");
                break;
            case "png":
                resp.setContentType("image/png;");
                break;
            case "css":
                resp.setContentType("text/css;charset=UTF-8");
                break;
            case "html":
                resp.setContentType("text/html;charset=UTF-8");
                break;
            default:
                resp.setContentType("text/plain;charset=UTF-8");
                break;
        }
        if (is == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, uri + " is not found!");
        } else {
            ServletOutputStream os = resp.getOutputStream();
            byte[] buffer = new byte[1024];
            int buff;
            while ((buff = is.read(buffer)) != -1) {
                os.write(buffer, 0, buff);
            }
            os.flush();
            os.close();
            is.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
