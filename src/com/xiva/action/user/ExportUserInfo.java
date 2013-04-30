package com.xiva.action.user;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.xiva.action.common.BasicAction;

public class ExportUserInfo extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String userName;
    

    
    public String exportZipFile() throws IOException
    {
        response.reset();
        response.setContentType("application/zip");
        
        String path = "userInfo.zip";
        String filename = path.substring(path.lastIndexOf("\\") + 1);
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

        return SUCCESS;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
}
