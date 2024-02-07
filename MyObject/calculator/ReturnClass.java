package calculation;

import java.util.ArrayList;
import java.io.File;
import main.*;

/**
* Returns the instances of all classes in this package, 
* If the class cannot be instantiated, skip it.
*/
public final class ReturnClass {
	/** This class cannot be instantiated. */
	private ReturnClass() {}
	
	/**
	* Gets can instance of {@link main.Calculation}'s classes.
	*
	* In fact, there are loopholes in this class.
	* The bottom layer uses File to call.
	* And errors may occur.
	* The bottom layer of instantiation also uses forName().
	* @param packagepath Required package name, excluding class name.
	* @param args Parameters required for instantiation.
	* @return 	Instances in this class, 
	* 			And already instantiated objects.
	* @see java.lang.Class#forName(String className)
	* @see RunningBackground#getCalculation()
	*
	*/
	public static ArrayList<Calculation> getAllClass(String packagepath, Object... args){
		if(packagepath == null) 
			packagepath = "";
		else 
			packagepath = packagepath.concat(".");
		ArrayList<Calculation> al = new ArrayList<>();
		File f = new File(new File("").getAbsolutePath() + "\\calculation");
		for(String str : f.list()) {
			//System.out.println(str);
			if(!str.endsWith(".class")) continue;
			Object o = null;
			Class c;
			try{
				c = Class.forName((packagepath + str).replaceAll("(\\.\\w*)$", ""));
				o = c.getConstructors()[0].newInstance(args);
				al.add((Calculation)o);
			}
			catch(Exception e) {
				//System.out.println("Ret--HasException");
			}
		}
		
		return al;
	}
}