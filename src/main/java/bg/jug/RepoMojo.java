package bg.jug;

import bg.jug.json.JsonUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

@Mojo(name = "search")
public class RepoMojo extends AbstractMojo {

    private static final String FULL = "full";

    @Parameter(property = "query", required = false)
    private String query;

    @Parameter(property = "size", required = false, defaultValue = "20")
    private String size;

    @Parameter(property = "full", required = false, defaultValue = FULL)
    private boolean isFull;


    public void execute() throws MojoExecutionException {
        getLog().info("Executing Search Query");

        try {
            JSONObject searchResponse = JsonUtil.readJsonFromUrl(
                            "https://search.maven.org/solrsearch/select?q=" + query + "&rows=" + size + "&wt=json");
            printDocs(searchResponse);
            printSuggestions(searchResponse);
            searchResponse.get("response");
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void printDocs(JSONObject searchResponse) {
        if (searchResponse.getJSONObject("response") != null) {
            JSONArray docs = searchResponse.getJSONObject("response").getJSONArray("docs");
            if (docs.length() > 0) {
                getLog().info("Results:");
                for (int i = 0; i < docs.length(); i++) {
                    JSONObject doc = docs.getJSONObject(i);
                    if (isFull) {
                        getLog().info("g:" + doc.getString("g"));
                        getLog().info("a:" + doc.getString("a"));
                        getLog().info("latestVersion:" + doc.getString("latestVersion"));
                        getLog().info("repositoryId:" + doc.getString("repositoryId"));
                        getLog().info("p:" + doc.getString("p"));
                    }
                    getLog().info(doc.getString("g") + ":" + doc.getString("a") + ":" + doc.getString("latestVersion"));
                    getLog().info("mvn..." + doc.getString("g") + ":" + doc.getString("a") + ":" + doc.getString("latestVersion"));
                    getLog().info("============================================");
                }
            }
        }
    }

    private void printSuggestions(JSONObject searchResponse) {
        if (searchResponse.getJSONObject("spellcheck") != null) {
            JSONArray suggestions = searchResponse.getJSONObject("spellcheck").getJSONArray("suggestions");
            if (suggestions.length() > 0) {
                StringBuilder suggestionsBuilder = new StringBuilder();
                getLog().info("Suggestions: ");
                for (int i = 0; i < suggestions.length(); i++) {
                    suggestionsBuilder.append(suggestions.getString(i)).append(", ");
                }
                getLog().info(suggestionsBuilder.toString());
            }
        }
    }

}
