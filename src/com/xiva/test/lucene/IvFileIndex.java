package com.xiva.test.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class IvFileIndex
{

    private static List<File> fileList = new ArrayList<File>(1024);

    public static void listAllFile(File fileDir)
    {
        File[] files = fileDir.listFiles();
        for (File file : files)
        {
            if (file.isDirectory())
            {
                listAllFile(file);
            }
            else
            {
                fileList.add(file);
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        File fileDir = new File("F:\\WorkSpace");
        File indexDir = new File("F:\\WorkSpace\\EclipseProjects\\luceneIndex");

        Analyzer luceneAnalyzer = new SmartChineseAnalyzer(Version.LUCENE_42);

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, luceneAnalyzer);
        config.setOpenMode(org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE);

        Directory fsDir = new SimpleFSDirectory(indexDir);
        IndexWriter indexWriter = new IndexWriter(fsDir, config);

        listAllFile(fileDir);
        long startTime = new Date().getTime();

        indexWriter.deleteAll();

        // 增加document到索引去
        for (File txtFile : fileList)
        {
            if (txtFile.isFile() && txtFile.getName().endsWith(".java"))
            {
                System.out.println(txtFile.getName());
                FileInputStream fis = null;
                try
                {
                    fis = new FileInputStream(txtFile);
                }
                catch (FileNotFoundException fnfe)
                {
                    continue;
                }

                try
                {
                    Document document = new Document();
                    Field fieldPath = new StringField("path", txtFile.getPath(), Field.Store.YES);
                    Field fieldBody = new TextField("body", new BufferedReader(new InputStreamReader(fis, "GBK")));

                    document.add(fieldPath);
                    document.add(fieldBody);
                    indexWriter.addDocument(document);
                }
                finally
                {
                    fis.close();
                }

                System.out.println("被索引文件:" + txtFile.getCanonicalPath());
            }
        }

        // 对索引进行优化
        indexWriter.forceMerge(10);

        indexWriter.close();

        // 测试一下索引的时间
        long endTime = new Date().getTime();
        System.out.println("索引耗费时间：" + (endTime - startTime) + " 毫秒!");
    }

}