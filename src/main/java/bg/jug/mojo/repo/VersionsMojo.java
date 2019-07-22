package bg.jug.mojo.repo;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import bg.jug.mojo.repo.json.JsonUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.utils.logging.MessageBuilder;
import org.apache.maven.shared.utils.logging.MessageUtils;
import org.codehaus.plexus.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Mojo(name = "versions")
public class VersionsMojo extends HttpClientAbstractMojo {

    @Parameter(property = "query", required = false)
    private String query;

    @Parameter(property = "size", required = false, defaultValue = "20")
    private String size;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Executing Versions Query: '" + query + "' with size of " + size);
        getLog().info("use -Dsize=number if you want to see less results (max 20)");

        try {
            StringBuilder urlBuilder = new StringBuilder("https://search.maven.org/solrsearch/select");
            if (StringUtils.isNotEmpty(query)) {
                if (query.contains(":")) {
                    String[] ga = query.split(":");
                    String group = ga[0];
                    String artifact = ga[1];
                    urlBuilder.append("?q=g:%22")
                              .append(group)
                              .append("%22+AND+a:%22")
                              .append(artifact)
                              .append("%22");
                } else {
                    urlBuilder.append("?q=g:%22").append(query).append("%22");
                }
            }
            urlBuilder.append("&core=gav&rows=").append(size).append("&wt=json");
            //@formatter:off
            //end url should look like https://search.maven.org/solrsearch/select?q=g:%22com.google.inject%22+AND+a:%22guice%22&core=gav&rows=20&wt=json
            //@formatter:on
            URL url = new URL(urlBuilder.toString());
            try (CloseableHttpClient client = createHttpClient(url)) {
                JSONObject searchResponse = JsonUtil.readJsonFromUrl(url, client);

                printVersions(searchResponse);
            }
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void printVersions(JSONObject searchResponse) {
        if (searchResponse.getJSONObject("response") != null) {
            JSONArray docs = searchResponse.getJSONObject("response").getJSONArray("docs");
            if (docs.length() > 0) {

                TreeMap<String, List<String>> gaVersions = getGaVersions(docs);

                MessageBuilder messageBuilder = MessageUtils.buffer();
                Set<String> gas = gaVersions.keySet();
                messageBuilder.info("Versions:").newline();
                for (String ga : gas) {
                    messageBuilder.strong(ga).newline();
                    List<String> versions = gaVersions.get(ga);

                    for (String version : versions) {
                        messageBuilder.a(version).newline();
                    }
                    messageBuilder.a("============================================").newline();
                }
                getLog().info(messageBuilder.toString());
            }
        }
    }

    private TreeMap<String, List<String>> getGaVersions(JSONArray docs) {
        TreeMap<String, List<String>> gaVersions = new TreeMap<>();
        for (int i = 0; i < docs.length(); i++) {
            JSONObject doc = docs.getJSONObject(i);
            String key = doc.getString("g") + ":" + doc.getString("a");
            if (gaVersions.get(key) != null) {
                gaVersions.get(key).add(doc.getString("v"));
            } else {
                List<String> versions = new LinkedList<>();
                versions.add(doc.getString("v"));
                gaVersions.put(key, versions);
            }
        }
        return gaVersions;
    }
}
