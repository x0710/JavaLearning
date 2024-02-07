import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class App {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://121.26.242.250:8001/jxshcj.asp");
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);

        urlConnection.setRequestProperty("cookie","njbj=111; kssja4=202210yk%20%20%20g1; myPassword=111;");
        BufferedReader is = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        for(String buffer;(buffer=is.readLine())!= null;) {
            System.out.println(buffer);
        }
        is.close();

    }
}
