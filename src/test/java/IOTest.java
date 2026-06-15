import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import fileShortCuts.TextAndObjSaveAndLoad;
import javafx.util.Pair;



public class IOTest 
{

	String dir = "someResources/";
	
	String fileName1 = "Hello Filesystem" + '1';
	
	String oldText = "hi \n U Fool!";
	
	String objFileName = "ObjectFile";

	@Test
	public void testIO() throws IOException, ClassNotFoundException
	{


		System.out.println("Old Text: "+oldText);
			
		TextAndObjSaveAndLoad.saveText(dir + fileName1, oldText);
		String newText = TextAndObjSaveAndLoad.loadText(dir + fileName1);
			
		System.out.println("New Text: "+newText);
			
		assert(oldText.equals(newText));//Loaded Text equals the saved one?
	
		Path path = Path.of(dir);
		boolean folderExists = Files.exists(path)&&Files.isDirectory(path);
			
		assert(folderExists);
		
		assert(new File(dir + fileName1).delete());//deletion must be successful.
		
		Pair<String, String> pair = new Pair<String, String>("key", "value");
		
		TextAndObjSaveAndLoad.saveObject(dir + objFileName, pair);
		
		path = Path.of(dir + objFileName);
		assert(Files.exists(path));
		
		@SuppressWarnings("unchecked")
		Pair<String, String> loadedPair = (Pair<String, String>) TextAndObjSaveAndLoad.loadObject(dir + objFileName);
	
		assert(pair.equals(loadedPair));
		
		assert(new File(dir + objFileName).delete());
	}
	
	@Test
	public void testIOPath() throws IOException, ClassNotFoundException
	{

		System.out.println("Old Text: "+oldText);

		Path path = Path.of(dir + fileName1);
		TextAndObjSaveAndLoad.saveText(path, oldText);
		String newText = TextAndObjSaveAndLoad.loadText(path);

		System.out.println("New Text: "+newText);
			
		assert(oldText.equals(newText));//Loaded Text equals the saved one?

		assert(path.toFile().delete());//deletion must be successful.

		Pair<String, String> pair = new Pair<String, String>("key", "value");

		path = Path.of(dir + objFileName);
		TextAndObjSaveAndLoad.saveObject(path, pair);

		assert(Files.exists(path));

		@SuppressWarnings("unchecked")
		Pair<String, String> loadedPair = (Pair<String, String>) TextAndObjSaveAndLoad.loadObject(path);

		assert(pair.equals(loadedPair));

		assert(path.toFile().delete());
	}		
}