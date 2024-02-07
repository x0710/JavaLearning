import java.io.*;

public class EasierTranslate {
	volatile static int line;
	static int all = 770612;
	volatile static boolean flag;
	
	public static void main(String[] args) throws Exception {
		/*
		String s = new java.util.Scanner(System.in).nextLine();
		System.out.println(s);
		String[] r = s.split(",");
		for(int i = 0;i<r.length;i++) System.out.println(r[i]);
		if(3>1)return;
		*/
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("G:/OneDrive/Desktop/work/ECDICT-master/ecdict.csv"), "UTF-8"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("G:/OneDrive/Desktop/work/ECDICT-master/ecdictTest.csv"), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		
		Thread t = new Thread() {
			public void run() {
				double percent;
				do {
					System.out.println((percent=line/(double)all*100)+"%");
					try {
						Thread.sleep(500);
					}
					catch(Exception e) {}
				}
				while(!flag);
			}
			
			
		};
		//t.setDaemon(true);
		t.start();
		
		int load = 0;
		int maxWord = 0, maxTrans = 0;
		for(String line;(line=br.readLine())!= null;) {
			EasierTranslate.line++;
			String[] split = line.split(",");
			boolean isPhrase = !(split[0].matches("^\\w+$"));
			if(isPhrase) continue;
			bw.write(line);
			bw.newLine();
			/*
			int i = 3;
			StringBuilder trans = new StringBuilder(split[i]);
			for(boolean isSearched = false;i < split.length;i++) {
				if(split[i].matches("^.*?[\\u4E00-\\u9FA5]+.*$")) {
					if(isSearched) trans.append(",");
					trans.append(split[i]);
					isSearched = true;
				}
				else if(isSearched) break;
			}
			sb.append(split[0]).append(",\"").append(trans.toString().replaceAll("\"|(\\\\n)", "").trim()).
				append("\",").append(isPhrase);
			load++;
			//System.out.println(sb.toString());
			maxWord = Math.max(split[0].length(), maxWord);
			maxTrans = Math.max(trans.length(), maxTrans);
			/*
			if(maxWord == 78) {
				System.out.println(split[0]);
				break;
			}
			if(maxTrans == 905) {
				System.out.println(trans);
				break;}*/
				/*
				for(String str:split) System.out.println(str);
				i=3;
				for(boolean isSearched = false;i < split.length;i++) {
					System.out.println("split["+i+"]:"+split[i]);
					if(split[i].matches("^.*?[\\u4E00-\\u9FA5]+.*$")) {
						System.out.println(true);
						if(isSearched) trans.append(",");
						trans.append(split[i]);
						isSearched = true;
					}
					else if(isSearched) break;
			}
				break;
			}
			sb.setLength(0);
			*/
		}
		flag = true;
		br.close();
		bw.flush();
		bw.close();
		
		System.out.println("完成，共有"+load+"条数据被记录--"+((double)load/all*100)+"%");
		System.out.println("跳过："+(all-load));
		System.out.println("最长单词长度："+maxWord+"    最长翻译长度："+maxTrans);
	}
}