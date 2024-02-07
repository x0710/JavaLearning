
import java.io.*;
import java.util.regex.*;

public class Rename
{
	String val, regex;
	File path;
	public boolean patternMatching = true;
	
	public Rename(File path, String regex, String val, boolean pattern)
	{
		this.regex = regex;
		this.path = path;
		this.val = val;
		patternMatching = pattern;
		
		if(path.isFile())
			fileMod(path);
		else
			folderMod();
		
	}
	void folderMod()
	{
		File[] renameFiles = path.listFiles();
		for(int i = 0;i < renameFiles.length;i++)
			if(renameFiles[i].isFile())
				if(!fileMod(renameFiles[i]))
					System.out.println(renameFiles[i].getPath() + "重命名失败");
				else
					System.out.println(renameFiles[i].getPath() + "重命名成功");
			else
				continue;
	}
	boolean fileMod(File f)
	{
		//获取名字
		String rename, name = f.getName();
		String pattern = "";
		if(patternMatching) {
			rename = name.replaceAll(regex, val);
		}
		else {
			Matcher t = Pattern.compile("(\\.\\w*)$").matcher(name);
			while(t.find()) 
				pattern = t.group();
			//System.out.println(pattern);
			rename = name.replace(pattern, "").replaceAll(regex, val);
			return f.renameTo(new File(f.getParent().concat("/") + rename.concat(pattern)));
		}
		//重命名
		return f.renameTo(new File(f.getParent().concat("/") + rename));
	}
	
}
