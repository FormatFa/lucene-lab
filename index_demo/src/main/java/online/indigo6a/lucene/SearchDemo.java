package online.indigo6a.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class SearchDemo {
    public static void main(String[] args) throws IOException, ParseException {
        String indexPath = "tempIndexData";
        Directory dir = FSDirectory.open(Paths.get(indexPath));

        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();

        // 查询文档数
        int count = searcher.count(new MatchAllDocsQuery());
        System.out.println("document count:"+count);

        // 使用lucene 查找
        QueryParser parser = new QueryParser("contents",analyzer);

        Scanner scanner = new Scanner(System.in);
        System.out.println("tip: input exit to exi");
        while(true) {
            System.out.println("input search text:");
            String input = scanner.nextLine();
            if("exit".equals(input)) {
                break;
            }
            Query query = parser.parse(input);
            System.out.println(query);
            TopDocs topDocs = searcher.search(query, 10);
            for(ScoreDoc doc: topDocs.scoreDocs) {
                Document document = searcher.storedFields().document(doc.doc);
                System.out.println(document.get("path"));
            }
        }

    }
}
