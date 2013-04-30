package com.xiva.common.util;

public class ZipUtil {

    public static final String password = "123456";
    public static final String winrarPath = "C:\\Program Files\\WinRAR\\WinRAR.exe";

    
    public static boolean unZipFile(String password)
    {
        boolean bool = false;

        String cmd = winrarPath + " x -p" + password + " " + "D:\\test.rar"
                + " " + "D:\\";

        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            if (proc.waitFor() != 0) {
                if (proc.exitValue() == 0) {
                    bool = false;
                }
            } else {
                bool = true;
                System.out.println(password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bool);
        return bool;
    }
    
    
    public static void main(String[] args) {
        
        unZipFile("951751");
        
        unZipFile("159753");
        
        unZipFile("753951");
    }
}
