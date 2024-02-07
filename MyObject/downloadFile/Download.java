import java.io.*;
import java.net.*;
import java.util.regex.*;

public class Download {
	/**
	 * 从网络Url中下载文件
	 * 
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static String downLoadFromUrl(String urlStr, String fileName, String savePath) {
		try {
 
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(500);
			// 防止屏蔽程序抓取而返回403错误
			// Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
 
			// 得到输入流
			InputStream inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);
 
			// 文件保存位置
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdir();
			}
			File file = new File(saveDir + File.separator + fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			// System.out.println("info:"+url+" download success");
			return saveDir + File.separator + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
 
	}
 
	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	public static String getWebsite(String urlStr) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			
			conn.setConnectTimeout(500);
			// 防止屏蔽程序抓取而返回403错误
			//Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
			//conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			InputStream inputStream = conn.getInputStream();
			byte[] buffer = new byte[512];
			for(int size;(size = inputStream.read(buffer))!= -1;) {
				sb.append(new String(buffer, 0, size, "UTF-8"));
			}
			inputStream.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		// downLoadFromUrl("https://blog.csdn.net/vison155142/article/details/76037647", "File", "./i");
		/*
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = new FileInputStream("web.html");
		byte[] buffer = new byte[512];
		for(int size;(size = inputStream.read(buffer))!= -1;) {
			sb.append(new String(buffer, 0, size));
		}
		inputStream.close();*/
		int page = 97;
		int index = 1152;
		while(page <= 194) {
			
			Pattern pattern = Pattern.compile("<a class=\"ctrl download\" photo=\"\\d{4,5}\" href=\"(.*?)\".*?title=\"(.*?)_.*?");
			Matcher matcher = pattern.matcher(getWebsite("https://bing.ioliu.cn/?p="+page));
			System.out.println((page++)+"页");
			while(matcher.find()) {
				//System.out.println(matcher.find(0));
				String url = matcher.group(1);
				if(!url.matches(".*UHD.*$")) {
					System.out.println("跳过："+url);
					continue;
				}
				System.out.println((index++)+"下载"+url+"中");
				downLoadFromUrl(url, matcher.group(2)+".jpg", "G:\\Pictures\\picture");
			}
		}
		/*
		System.out.println(getWebsite("https://bing.ioliu.cn/?p=2"));
		System.exit(0);*/
		
		
	}
}