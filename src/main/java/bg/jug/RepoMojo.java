package bg.jug;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "search")
public class RepoMojo extends AbstractMojo {

    @Parameter(property = "query", required = false)
    private String query;

    public void execute() throws MojoExecutionException {
        getLog().info("Executing Search Query");

    }

    private void executeInstall() {
    }

    private void executeSearch(String search) {
    }

}
