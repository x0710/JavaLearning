import java.net.*;
import java.io.*;
import java.util.regex.*;

public class YiZhongInf2 {
	public static void main(String[] args) throws Exception {
		File wirteFile = new File("./file/stuinf23.csv");
		//Filw writeHTMLPath = new File("./html");
		BufferedReader fr = new BufferedReader(new FileReader(wirteFile));
		for(String readLine;(readLine=fr.readLine())!= null;) {
			String code = readLine.substring(7, 19);
			getPicFromZKZPK(code, new File("./file/pic/"+code+".jpg"));
			
		}
		fr.close();
	}
	
	//将一个args数组写入一个file
	public static void writeStringArrayToCSV(String[] args, File file) throws IOException {
		FileWriter fw = new FileWriter(file, true);
		for(int i = 0;i < args.length;i++) {
			String ws = args[i];
			if(ws==null) {
				ws = "";
				System.out.print("nul,");
			}
			fw.write(ws);
			if(i+1<args.length) fw.write(",");
		}
		fw.write("\r\n");
		fw.flush();
		fw.close();
	}
	
	/**
	* 从zkzpk中获得照片url，并写入文件path中
	*/
	public static void getPicFromZKZPK(String stuNo, File path) {
		try {
			URL ur1 = new URL("http://121.26.242.250:8001/zkzpk/"+stuNo+".jpg");
			InputStream is = ur1.openStream();
			OutputStream os = new FileOutputStream(path);
			byte[] buffer = new byte[216];
			for(int size;(size=is.read(buffer))!= -1;) {
				os.write(buffer, 0, size);
			}
			os.flush();
			os.close();
			is.close();
			System.out.println(stuNo+"'s Picture Download!");
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println(stuNo+"'s Download Fail!");
		}
	}
	
	/**
	* 从qjdlmwa.asp中获取信息
	* yzxh=(\d+?)&nj=[\s\S]+?\/(\d+?)\.jpg"[\s\S]+?<br>\s+(.*?)&nbsp[\s\S]+?\.(\d+?)\D[\s\S]+?宿舍: (.+?)\s
	* 包括stuNo,yzNo,Name,Class,Dorm五个属性
	*/
	public static String[] getNoNoNameClassDormFromQJDLMWA(int no) throws IOException {
		StringBuilder sb = getHTML("http://121.26.242.250:8001/qjdlmwa.asp?yzxh="+no);
		//System.out.println(sb);
		Pattern p = Pattern.compile("yzxh=(\\d+?)&nj=[\\s\\S]+?\\/(\\d+?)\\.jpg[\\s\\S]+?<br>\\s+(.*?)&nbsp[\\s\\S]+?\\.(\\d+?)\\D[\\s\\S]+?\u5bbf\u820d: (.+?)\\s");
		Matcher m = p.matcher(sb);
		String[] ret = new String[5]; // 正则中只有5个组
		
		if(m.find()) {
			for(int i = 1;i <= ret.length;i++) {
				ret[i-1] = m.group(i);
			}
		}
		return ret;
	}
	
	// 读取一个网页
	public static StringBuilder getHTML(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
		StringBuilder sb = new StringBuilder();
		for(String val;(val=br.readLine())!= null;) {
			sb.append(val).append("\r\n");
		}
		br.close();
		return sb;
	}
	/*
	public static void wirteHTML(StringBuilder sb, File file) throws IOException {
		FileWriter wf = new FileWriter(file);
		wf.write(sb.toString());
		wf.flush();
		wf.close();
	}
	*/
}