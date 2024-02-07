import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.text.*;
public class YiZhongInf
{
	public static void main(String[] args) throws Exception {
		gSMoreInf();
	}
	public static void deal() throws Exception{
		File source = new File("./studentInf.inf");
		BufferedReader br = new BufferedReader(new FileReader(source));
		BufferedWriter bw = new BufferedWriter(new FileWriter("outData20.csv"));
		Pattern p = Pattern.compile("7\">(.*?)<[\\s\\S]*?(\\d{6})<br>(.*?)<br>[\\s\\S]*?:(.*)<\\/font><a");
		for(String val;(val=br.readLine())!= null;) {
			Matcher m = p.matcher(val);
			if(m.find()) {
				String no=m.group(2),
					classNo = m.group(1),
					name = m.group(3),
					hotel = m.group(4);
				String[] t = classNo.split("\\.");
				String grade = t[0], class_ = t[1].substring(0, t[1].length()-1);
				StringBuilder sb = new StringBuilder();
				sb.append(no).append(",");
				sb.append(20).append(",");
				sb.append(class_).append(",");
				sb.append(hotel).append(",");
				sb.append(name);
				bw.write(sb.toString());
				bw.newLine();
				bw.flush();
			}
		}
		bw.flush();
		bw.close();
		br.close();
	}
	public static void setPic() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("./outData20.csv"));
		for(String val;(val=br.readLine())!= null;) {
			try {
				getPic(Integer.parseInt(val.substring(0, 6)));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		br.close();
	}
	public static void getPic(int id) throws Exception{
		URL u1 = new URL("http://121.26.242.250:8001/zpk/"+id+".jpg");
		InputStream is = u1.openStream();
		OutputStream os = new FileOutputStream("C:/Users/15940/Pictures/s/"+id+".jpg");
		byte[] buffer = new byte[216];
		for(int size;(size=is.read(buffer))!= -1;) {
			os.write(buffer, 0, size);
		}
		os.flush();
		os.close();
		is.close();
		System.out.println(id+" download!");
	}
	public static void gSMoreInf() throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter("./moreInf23.csv", false));
		/*BufferedReader br = new BufferedReader(new FileReader("./outData.csv"));
		
		for(String val;(val=br.readLine())!= null;) {
			try {
				StringBuilder sb = new StringBuilder();
				int len = Integer.parseInt(val.substring(0, 6));
				for(String s:getMoreInf(len, 1)) {
					sb.append(s).append(",");
				}
				sb.deleteCharAt(sb.length()-1);
				bw.write(sb.toString());
				bw.newLine();
				bw.flush();
				System.out.println(len+" download!");
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			//sb.append(val).append("\r\n");
		}
		br.close();
		*/
		for(int no = 230000;no <= 237000;no++) {
			try {
				StringBuilder sb = new StringBuilder();
				for(String s:getMoreInf(no, 1)) {
					sb.append(s).append(",");
				}
				sb.deleteCharAt(sb.length()-1);
				bw.write(sb.toString());
				bw.newLine();
				bw.flush();
				System.out.println(no+" download!");
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		bw.close();
	}
	public static String[] getMoreInf(int code, int grade) throws Exception{
		URL url = new URL("http://121.26.242.250:8001/jxsxsxx.asp?yzxh="+code+"&nj="+grade);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
		StringBuilder sb = new StringBuilder();
		for(String val;(val=br.readLine())!= null;) {
			sb.append(val).append("\r\n");
		}
		br.close();
		int columns = 8;
		String[] ret = new String[columns];
		//匹配乌俊鹏,10,220125,蒙古族,学校,身份证,地址,电话
		//strong>.+\">(.*)\\s{2}<\\/font[\\s\\S]+3\"><b>(.*)<\\/b>[\\s\\S]*>(\\d{6})<[\\s\\S]*\"#0000FF\">(.*?)<[\\s\\S]*\"3\">(.*?)<b>[\\s\\S]*\"3\">(\\d{17}\\w)[\\s\\S]*\"3\">(.*?)<\\/font>[\\s\\S]*:(\\d{11})?<
		Pattern p = Pattern.compile("strong>.+\">(.*)\\s{2}<\\/font[\\s\\S]+3\"><b>(.*)<\\/b>[\\s\\S]*>(\\d{6})<[\\s\\S]*\"#0000FF\">(.*?)<[\\s\\S]*\"3\">(.*?)<b>[\\s\\S]*\"3\">(\\d{17}\\w)[\\s\\S]*\"3\">(.*?)<\\/font>[\\s\\S]*:(\\d{11})?<");
		Matcher m = p.matcher(sb);
		if(m.find()) {
			for(int i = 1;i <= columns;i++) {
				ret[i-1] = m.group(i);
			}
		}
		//-----
		try {
			ret[1] = ret[1].substring(3, 5);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	public static void getInf() throws Exception {
		String urlString="http://121.26.242.250:8001/jxsxsxx.asp?Yzxh=22";
		DecimalFormat df = new DecimalFormat("0000");
		File target = new File("./studentInf.inf");
		BufferedWriter bos = new BufferedWriter(new FileWriter(target, false));
		for(int i = 0;i <=7000;i++) {
			String u = urlString+df.format(i);
			StringBuilder sb = new StringBuilder();
			//System.out.println(sb);


			try {
				URL url = new URL(u);
				BufferedReader is = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
				
				for(String val;(val=is.readLine())!= null;) {
					sb.append(val);
				}
				is.close();
				bos.write(i+sb.toString());
				bos.newLine();
				bos.flush();
				System.out.println(u);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		bos.flush();
		bos.close();
	}
}
