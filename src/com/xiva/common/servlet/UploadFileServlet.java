package com.xiva.common.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiva.common.util.ParameterUtil;

/**
 * 上传servlet
 * 
 * @author xiva
 * 
 */
public class UploadFileServlet extends HttpServlet
{

    /**
     * 
     */
    private static final long serialVersionUID = -4189012235755765066L;

    private static Log log = LogFactory.getFactory().getInstance(UploadFileServlet.class);

    public void init() throws ServletException
    {
    }

    public UploadFileServlet()
    {
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("doPost start!");
        request.setCharacterEncoding("UTF-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        try
        {
            List<FileItem> fileList = fileUpload.parseRequest(request);
            if (fileList != null)
            {
                int listSize = fileList.size();
                final String filePath = ParameterUtil.getPropByKey("uploadFilePath");
                for (int i = 0; i < listSize; i++)
                {
                    final FileItem fileItem = fileList.get(i);
                    // 创建线程启动文件上传
                    Thread thread = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            InputStream is = null;
                            OutputStream os = null;
                            BufferedOutputStream bos = null;
                            try
                            {
                                is = fileItem.getInputStream();
                                UUID uuid = UUID.randomUUID();
                                String fileName = uuid.toString() + fileItem.getName();

                                File file = new File(filePath + fileName);
                                os = new FileOutputStream(file);
                                bos = new BufferedOutputStream(os);
                                byte b[] = new byte[1024];
                                while (is.read(b) != -1)
                                {
                                    bos.write(b);
                                }
                                bos.flush();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            finally
                            {
                                if (is != null)
                                {
                                    try
                                    {
                                        is.close();
                                    }
                                    catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    finally
                                    {
                                        if (os != null)
                                        {
                                            try
                                            {
                                                os.close();
                                            }
                                            catch (IOException e)
                                            {
                                                e.printStackTrace();
                                            }
                                            finally
                                            {
                                                if (bos != null)
                                                {
                                                    try
                                                    {
                                                        bos.close();
                                                    }
                                                    catch (IOException e)
                                                    {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                    thread.start();
                }
            }

        }
        catch (FileUploadException e)
        {
            e.printStackTrace();
        }
        log.info("doPost end!");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    public void destroy()
    {
        super.destroy();
    }
}
