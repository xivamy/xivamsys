package com.xiva.common.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 上传servlet
 * 
 * @author xiva
 * 
 */
public class DownloadFileServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -4189012235755765066L;

    public void init() throws ServletException {
    }

    public DownloadFileServlet() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = "file.zip";
        String filename = path.substring(path.lastIndexOf("\\") + 1);
        response.setContentType("application/zip");
        response.setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode(filename, "UTF-8"));

        OutputStream out = null;
        try {
            out = response.getOutputStream();// 输出流

            ZipOutputStream zos = new ZipOutputStream(response
                    .getOutputStream());

            zos.putNextEntry(new ZipEntry("file.txt"));
            zos.write("Xiva aaabbbuser data".getBytes());
            zos.closeEntry();
            zos.flush();
            zos.close();
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void destroy() {
        super.destroy();
    }
}
