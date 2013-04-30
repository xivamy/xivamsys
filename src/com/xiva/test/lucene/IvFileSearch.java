package com.xiva.test.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IvFileSearch
{
    public static void main(String[] args) throws IOException
    {
        String queryString = "索引";
        String field = "body";
        Query query = null;
        TopDocs docs = null;

        File indexDir = new File("F:\\WorkSpace\\EclipseProjects\\luceneIndex");
        IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));
        IndexSearcher searcher = new IndexSearcher(reader);

        // StopFilterFactory factory = new StopFilterFactory();
        // factory.getStopWords()
        Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_42);

        try
        {
            long startTime = new Date().getTime();
            QueryParser qp = new QueryParser(Version.LUCENE_42, field, analyzer);
            query = qp.parse(queryString);

            long endTime = new Date().getTime();
            System.out.println("索引耗费时间：" + (endTime - startTime) + " 毫秒!");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        if (searcher != null)
        {
            docs = searcher.search(query, 25);// 可以分页查询
            ScoreDoc scoreDocs[] = docs.scoreDocs;

            for (int i = 0; i < docs.totalHits; i++)
            {
                Document targetDoc = searcher.doc(scoreDocs[i].doc);
                String path = targetDoc.get("path");
                System.out.println("path:" + path);
            }
        }
    }
}