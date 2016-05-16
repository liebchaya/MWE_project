package milaCorpora;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UnifyTextCorpora2File {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File inputFolder = new File("F:\\MilaLemma");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("H:\\MilaText1File\\milaNewsLemmaCorpora.txt"), "UTF-8"));
		for (File f:inputFolder.listFiles()){
			if(!f.getName().contains("wiki")){
				BufferedReader reader = new BufferedReader(new FileReader(f));
	//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("H:\\MilaTextFiles\\"+f.getName()), "UTF-8"));
				String line = reader.readLine();
				while (line!=null){
					writer.write(line.split("#")[2]+"\n");
					line = reader.readLine();
				}
	//			writer.close();
				reader.close();
			}
		}
		writer.close();
//		convertFile();
	}
	
	
	public static void convertFile() throws IOException{
		 Path p = Paths.get("F:\\testMe.txt");
	     ByteBuffer bb = ByteBuffer.wrap(Files.readAllBytes(p));
//	     CharBuffer cb = Charset.forName("windows-1255").decode(bb);
//	     bb = Charset.forName("UTF-8").encode(cb);
	     CharBuffer cb = Charset.forName("UTF-8").decode(bb);
	     bb = Charset.forName("windows-1255").encode(cb);
	     Files.write(p, bb.array());
	}

}
