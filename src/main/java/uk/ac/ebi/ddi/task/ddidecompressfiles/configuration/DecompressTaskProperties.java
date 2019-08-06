package uk.ac.ebi.ddi.task.ddidecompressfiles.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties("decompressfiles")
public class DecompressTaskProperties {

    private Resource originalDirectory;

    private Resource targetDirectory;

    private boolean  deleteOriginal;

    public Resource getOriginalDirectory() {
        return originalDirectory;
    }

    public void setOriginalDirectory(Resource originalDirectory) {
        this.originalDirectory = originalDirectory;
    }

    public Resource getTargetDirectory() {
        return targetDirectory;
    }

    public void setTargetDirectory(Resource targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public boolean isDeleteOriginal() {
        return deleteOriginal;
    }

    public void setDeleteOriginal(boolean deleteOriginal) {
        this.deleteOriginal = deleteOriginal;
    }
}
