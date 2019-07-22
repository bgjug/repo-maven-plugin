package bg.jug.mojo.repo;

import java.io.IOException;
import java.net.URL;

import bg.jug.mojo.repo.json.JsonUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.utils.logging.MessageBuilder;
import org.apache.maven.shared.utils.logging.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Mojo(name = "search")
public class SearchMojo extends HttpClientAbstractMojo {

    private static final String FULL = "full";

    @Parameter(property = "query", required = false)
    private String query;

    @Parameter(property = "size", required = false, defaultValue = "20")
    private String size;

    @Parameter(property = "full", required = false, defaultValue = FULL)
    private boolean isFull;

    public void execute() throws MojoExecutionException {
        getLog().info("Executing Search Query: '" + query + "'");

        try {
            String urlStr =
                "https://search.maven.org/solrsearch/select?q=" + query + "&rows=" + size + "&wt=json";
            URL url = new URL(urlStr);
            try (CloseableHttpClient client = createHttpClient(url)) {
                JSONObject searchResponse = JsonUtil.readJsonFromUrl(url, client);

                printDocs(searchResponse);
                printSuggestions(searchResponse);
            }

        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void printDocs(JSONObject searchResponse) {
        if (searchResponse.getJSONObject("response") != null) {
            JSONArray docs = searchResponse.getJSONObject("response").getJSONArray("docs");
            if (docs.length() > 0) {

                MessageBuilder messageBuilder = MessageUtils.buffer();
                messageBuilder.info("Results:").newline();
                for (int i = 0; i < docs.length(); i++) {
                    JSONObject doc = docs.getJSONObject(i);
                    messageBuilder.strong(doc.getString("g") + ":" + doc.getString("a") + ":" + doc.getString("latestVersion")).newline();
                    if (isFull) {
                        messageBuilder.a("g:" + doc.getString("g")).newline();
                        messageBuilder.a("a:" + doc.getString("a")).newline();
                        messageBuilder.a("latestVersion:" + doc.getString("latestVersion")).newline();
                        messageBuilder.a("repositoryId:" + doc.getString("repositoryId")).newline();
                        messageBuilder.a("p:" + doc.getString("p")).newline();
                        messageBuilder.strong("mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=" + doc.getString("g") + ":" + doc.getString("a") + ":" + doc.getString("latestVersion")).newline();
                    }
                    messageBuilder.a("============================================").newline();
                }
                getLog().info(messageBuilder.toString());
            }
        }
    }

    private void printSuggestions(JSONObject searchResponse) {
        if (searchResponse.getJSONObject("spellcheck") != null) {
            JSONArray suggestions = searchResponse.getJSONObject("spellcheck").getJSONArray("suggestions");
            if (suggestions.length() > 1) { //0 is the query, 1 are the actual suggestions
                StringBuilder suggestionsBuilder = new StringBuilder();

                JSONArray suggestionsValue = suggestions.getJSONObject(1).getJSONArray("suggestion");
                getLog().info("Suggestions: ");
                for (int i = 0; i < suggestionsValue.length(); i++) {
                    suggestionsBuilder.append(suggestionsValue.getString(i)).append(", ");
                }
                getLog().info(suggestionsBuilder.toString());
            }
        }
    }

}
