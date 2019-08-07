package uk.ac.ebi.ddi.task.ddidecompressfiles;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.ddi.task.ddidecompressfiles.configuration.DecompressTaskProperties;
import uk.ac.ebi.ddi.task.ddidecompressfiles.service.DecompressFileService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DdiDecompressFilesApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(properties = {
        "decompressfiles.originalDirectory=file:/tmp/prod/ega",
        "decompressfiles.targetDirectory=file:/tmp/prod/ega",
        "decompressfiles.deleteOriginal=true"
})
public class ITDecompressFileService {

    @Autowired
    private DecompressTaskProperties decompressTaskProperties;

    @Autowired
    private DecompressFileService decompressFileService;

    @Test
    public void contextLoads() throws Exception {
        decompressFileService.decompress(decompressTaskProperties.getTargetDirectory(),
                decompressTaskProperties.getOriginalDirectory(), decompressTaskProperties.isDeleteOriginal());
        Path path = Paths.get(decompressTaskProperties.getTargetDirectory().getURI());
        Assert.assertTrue(Files.exists(path));
    }
}
