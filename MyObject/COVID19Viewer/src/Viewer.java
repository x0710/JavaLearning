import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.text.*;
import java.util.*;
import javax.net.ssl.*;

public class Viewer
{
	private URL u;
	private long confirmedCases, deathCases;
	private Date time;
	private String range;
	private boolean exception, canBack;
	
	public long getConfirmedCases() {
		return confirmedCases;
	}
	public long getDeathCases() {
		return deathCases;
	}
	public Date getTime() {
		return time;
	}
	public String getRange() {
		return range;
	}
	public Viewer()
	{
		this(true);
	}
	public Viewer(boolean back)
	{
		handle();
		canBack = back;
	}
	private void handle()
	{
		try
		{
			u = new URL("https://covid19.who.int");
			setValue(getStreamData());
			return;
		}
		catch(SSLProtocolException e)
		{
			System.out.println("发生错误，正在重连");
			if(canBack){
				handle();
				return;
			}
		}
		catch(UnknownHostException e)
		{
			System.out.println("请检查网络连接，网址是否过时");
		}
		catch(ParseException e)
		{
			System.out.println("格式化时间时发生错误");
		}
		catch(MalformedURLException | NullPointerException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("发生IO错误");
		}
		catch(Exception e)
		{
			System.out.println("数据接收失败");
		}
		exception = true;
	}
	StringBuilder getStreamData() throws IOException
	{
		System.out.println("正在连接服务器");
		BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));
		System.out.println("开始接收数据...");
		StringBuilder sb = new StringBuilder(10*10000);
		for(String t;(t = br.readLine()) != null;)
			sb.append(t);
		br.close();
		return sb;
	}
	void setValue(CharSequence val) throws ParseException
	{
		System.out.println("数据处理中");
		Pattern regex = Pattern.compile("<span\\sclass=\".*?\">(.*?)<\\/span>");
		Matcher main = regex.matcher(val);
		Matcher spare = Pattern.compile("<span\\sclass=\".*?\">([\\d,]+)<!--\\s-->").matcher(val);
		if(main.find())
			range = main.group(1);
		
		
		if(main.find())
			time = new SimpleDateFormat("hh:mmaa dd MMMMM yyyy", Locale.US).parse(main.group(1).replaceAll("CES?T,\\s", ""));
		time.setHours(time.getHours() + 6);
		
		
		if(spare.find())
			confirmedCases = Long.parseLong(spare.group(1).replace(",", ""));
		if(spare.find())
			deathCases = Long.parseLong(spare.group(1).replace(",", ""));
		
	}

	@Override
	public String toString()
	{
		if(exception)
			return "";
		// TODO: Implement this method
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  kk:mm ");
		StringBuilder sb = new StringBuilder(128);
		DecimalFormat df = new DecimalFormat("####,####,####");
		//2:16pm CEST, 18 August 2020
		//2:16pm 18 August 2020
		
		sb.append("统计时间：" + sdf.format(time) + "\r\n");
		sb.append("区域范围：" + range + "\r\n");
		sb.append("确诊病例：" + df.format(confirmedCases) + "\r\n");
		sb.append("死亡病例：" + df.format(deathCases) + "\r\n");
		sb.append("数据来源：" + u.getHost());
		
		return sb.toString();
	}
}
