package bg.jug;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Mojo(name = "install")
public class DependencyInstallMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", required = true, readonly = true )
    private MavenProject project;

    @Parameter(required = true, property = "gav")
    private String gav;

    public void execute() throws MojoExecutionException, MojoFailureException {
        File pomFile = project.getFile();
        try {
            Model model = loadModel(pomFile);
            Coordinates coords = getCoordinates();
            if (coords == null) {
                throw new MojoExecutionException("Wrong coordinates");
            }
            boolean added = addDependency(model, coords.groupId, coords.artifactId, coords.version);
            if (added) {
                try {
                    writeModel(model, pomFile);
                    getLog().info("Dependency " + coords + " was successfully added");
                } catch (IOException e) {
                    throw new MojoExecutionException("Could not store pom file", e);
                }
            }
        } catch (IOException ioe) {
            throw new MojoExecutionException("Could not load project pom");
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

    private Model loadModel(File pomFile) throws IOException {
        return new DefaultModelReader().read(pomFile, null);
    }

    private boolean addDependency(Model pom, String groupId, String artifactId, String version) {
        Dependency dependency = checkExistence(pom, groupId, artifactId, version);
        if (dependency != null) {
            getLog().warn("There is already dependency to " + groupId + ":" + artifactId + " in this project.");
            if (version == null || version.equals(dependency.getVersion())) {
                return false;
            }
            getLog().warn("It will be updated to version " + version);
            pom.removeDependency(dependency);
        }

        Dependency newDependency = new Dependency();
        newDependency.setGroupId(groupId);
        newDependency.setArtifactId(artifactId);
        if (version != null) {
            newDependency.setVersion(version);
        }
        pom.addDependency(newDependency);
        return true;
    }

    private Dependency checkExistence(Model pom, String groupId, String artifactId, String version) {
        List<Dependency> dependencies = pom.getDependencies();
        for (Dependency dependency : dependencies) {
            if (dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)) {
                return dependency;
            }
        }
        return null;
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

        @Override
        public String toString() {
            return groupId + ':' + artifactId + ':' + version;
        }
    }
}
