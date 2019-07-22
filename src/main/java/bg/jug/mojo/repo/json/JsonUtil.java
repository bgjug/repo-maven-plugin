package bg.jug.mojo.repo.json;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;

public class JsonUtil {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(URL url, CloseableHttpClient httpClient) throws IOException {
        HttpClientContext httpContext = HttpClientContext.create();
        HttpGet httpMethod = new HttpGet(url.toString());
        HttpResponse response = httpClient.execute(httpMethod, httpContext);
        int status = response.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK) {
            throw new IOException("Invalid response status: " + status);
        }

        try (InputStream is = response.getEntity().getContent()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}
