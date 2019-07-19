package bg.jug;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelWriter;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.*;

@Mojo(name = "install")
public class DependencyInstallMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", required = true, readonly = true )
    private MavenProject project;

    @Parameter(required = true, property = "gav")
    private String gav;

    public void execute() throws MojoExecutionException, MojoFailureException {
        File pomFile = project.getFile();
        Model model = loadModel(pomFile);
        Coordinates coords = getCoordinates();
        if (coords == null) {
            throw new MojoExecutionException("Wrong coordinates");
        }
        addDependency(model, coords.groupId, coords.artifactId, coords.version);
        try {
            writeModel(model, pomFile);
        } catch (IOException e) {
            throw new MojoExecutionException("Could not store pom file", e);
        }
    }

    private Coordinates getCoordinates() {
        String[] coords = gav.split(":");
        if (coords.length < 2) {
            getLog().error("You need to provide at least group ID and artifact ID");
            return null;
        }
        return new Coordinates(coords[0], coords[1], coords.length > 2 ? coords[2] : null);
    }

    private void writeModel(Model model, File pomFile) throws IOException {
        ModelWriter writer = new DefaultModelWriter();
        writer.write(pomFile, null, model);
    }

    private Model loadModel(File pomFile) {
        try (InputStream is = new FileInputStream(pomFile)) {
            return new MavenXpp3Reader().read(is);
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException();
        }
    }

    private void addDependency(Model pom, String groupId, String artifactId, String version) {
        Dependency newDependency = new Dependency();
        newDependency.setGroupId(groupId);
        newDependency.setArtifactId(artifactId);
        if (version != null) {
            newDependency.setVersion(version);
        }
        pom.addDependency(newDependency);
    }

    private static class Coordinates {
        private final String groupId;
        private final String artifactId;
        private final String version;

        private Coordinates(String groupId, String artifactId, String version) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }
    }
}
