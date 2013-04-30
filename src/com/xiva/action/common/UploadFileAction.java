package com.xiva.action.common;

import java.io.File;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiva.service.user.UserService;

public class UploadFileAction {

    private static Log log = LogFactory.getFactory().getInstance(UploadFileAction.class);

    private File  ebook;
    
    private String ebookFileName;

    @Autowired
    private UserService userService;
    
    public File getEbook() {
        return ebook;
    }
    public void setEbook(File ebook) {
        this.ebook = ebook;
    }
    
    public String getEbookFileName() {
        return ebookFileName;
    }
    public void setEbookFileName(String ebookFileName) {
        this.ebookFileName = ebookFileName;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public void uploadBook()
    {
        log.info("uploadBook start");
        log.info(ebookFileName);
        userService.login(null);
        log.info("uploadBook end");
    }
}
