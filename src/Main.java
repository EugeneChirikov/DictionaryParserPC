import com.mates120.dictionaryparser.DictionaryParser;
import com.mates120.dictionaryparser.debug.Logger;
import com.mates120.dictionaryparser.debug.SystemOut;

public class Main
{
	private static final String FILES_PATH = "/home/eugene/Documents/testdict";
	
	public static void main(String[] argv)
	{
		try
		{
			Logger.l().setPrinter(new SystemOut());
			TestStorage storage = new TestStorage();
			establishStorageConnection(storage);
			runParser(storage);
		} catch (Exception e)
		{
		   System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	private static void runParser(TestStorage storage)
	{
		DictionaryParser.run(storage, FILES_PATH);
	}
	
	private static void establishStorageConnection(TestStorage storage) throws Exception
	{		
		storage.establishConnection();
		System.out.println("Opened database successfully");
	}
}
