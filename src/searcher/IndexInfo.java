package searcher;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexInfo {

	public IndexInfo(String indexDir) throws IOException {
		Directory directory = FSDirectory.open(new File(indexDir));
		DirectoryReader reader = DirectoryReader.open(directory);  
		m_indexSearcher = new IndexSearcher(reader);  
	}
	
	
	public int getIndexSize(){
		return m_indexSearcher.getIndexReader().numDocs();
	}
	
	public String getSentenceContent(int docId) throws IOException{
		return m_indexSearcher.getIndexReader().document(docId).get("TERM_VECTOR");
	}
	
	private IndexSearcher m_indexSearcher = null;
	
	
}
