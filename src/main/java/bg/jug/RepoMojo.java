package bg.jug;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "search")
public class RepoMojo extends AbstractMojo {

    /**
     * @parameter expression="${query}"
     */
    private String query;

    public void execute() throws MojoExecutionException {
        getLog().info("Hello, world." + query);
    }

}
