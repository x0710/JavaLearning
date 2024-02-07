import java.io.File;
import java.util.regex.*;

public class DeleteFiles {
	public static void main(String[] args) {
		File startSearch = new File("G:\\OneDrive");
		recursive(startSearch);
	}
	static Pattern p = Pattern.compile("^(.*)-DESKTOP-UQ4FFJ7\\..*$");
	public static void recursive(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			
			for(File f : files) {
				recursive(f);
			}
		}
		else {
			String name = file.getName();
			Matcher m = p.matcher(name);
			if(m.find()) {
				File newFile = new File(file.getAbsolutePath().replaceAll("-DESKTOP-UQ4FFJ7", ""));
				System.out.println("OLD: "+file);
				System.out.println("NEW: "+newFile);
				boolean is = file.renameTo(newFile);
				System.out.println(is);
				//if(is) System.out.println("DELETE: "+file.delete());
			}
		}
	}
}