package uk.ac.ebi.ddi.task.ddidecompressfiles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uk.ac.ebi.ddi.task.ddidecompressfiles.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class DecompressFileService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DecompressFileService.class);

    public static final String ZIP_FILE_EXTENSION = "zip";
    public static final String GZIP_FILE_EXTENSION = "gz";
    public static final int BUFFER_SIZE = 1024;

    public void decompress(Resource origDir, Resource trgtDir, boolean dltOriginal) throws FileNotFoundException, IOException {

        File targetDir = trgtDir.getFile();
        File originDir = origDir.getFile();

        Assert.state(targetDir.exists() && targetDir.isDirectory(),
                "Target output directory must exists");

        if (originDir.isDirectory()) {
            File[] files = originDir.listFiles();
            if (files == null) {
                throw new FileNotFoundException();
            }
            for (File compressedFile : files) {
                LOGGER.info("Decompressing {}", compressedFile.getAbsolutePath());

                String fileExtension = FileUtil.getFileExtension(compressedFile).toLowerCase();

                switch (fileExtension) {
                    case ZIP_FILE_EXTENSION:
                        FileUtil.unzip(compressedFile, targetDir);
                        break;
                    case GZIP_FILE_EXTENSION:
                        FileUtil.gunzip(compressedFile, targetDir);
                        break;
                    default:
                        break;
                }
                FileUtil.deleteOriginalCompressedFile(compressedFile, dltOriginal);
            }
        }
    }
}
