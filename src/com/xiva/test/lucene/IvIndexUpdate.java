package com.xiva.test.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class IvIndexUpdate
{
    public static void updateIndex() throws Exception
    {
        File fileDir = new File("E:\\data\\lucene");
        File indexDir = new File("E:\\data\\index");

        Analyzer luceneAnalyzer = new SmartChineseAnalyzer(Version.LUCENE_42);

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, luceneAnalyzer);

        config.setOpenMode(org.apache.lucene.index.IndexWriterConfig.OpenMode.APPEND);

        Directory fsDir = new SimpleFSDirectory(indexDir);
        IndexWriter indexWriter = new IndexWriter(fsDir, config);
        File[] txtFiles = fileDir.listFiles();
        long startTime = new Date().getTime();

        // 增加document到索引去
        for (int i = 0; i < txtFiles.length; i++)
        {
            if (txtFiles[i].isFile() && txtFiles[i].getName().endsWith("u.txt"))
            {
                FileInputStream fis;
                try
                {
                    fis = new FileInputStream(txtFiles[i]);
                }
                catch (FileNotFoundException fnfe)
                {
                    continue;
                }

                try
                {
                    Document document = new Document();
                    Field fieldPath = new StringField("path", txtFiles[i].getPath(), Field.Store.YES);
                    Field fieldBody = new TextField("body", new BufferedReader(new InputStreamReader(fis, "GBK")));
                    
                    document.add(fieldPath);
                    document.add(fieldBody);

                    indexWriter.updateDocument(new Term("path", txtFiles[i].getPath()), document);
                }
                finally
                {
                    fis.close();
                }

                System.out.println("被更新索引文件:" + txtFiles[i].getCanonicalPath());
            }
        }

        indexWriter.forceMerge(10);
        indexWriter.close();

        // 测试一下索引的时间
        long endTime = new Date().getTime();
        System.out.println("更新索引耗费时间：" + (endTime - startTime) + " 毫秒!");
    }

    public static void main(String[] args) throws Exception
    {
        updateIndex();
    }
}
