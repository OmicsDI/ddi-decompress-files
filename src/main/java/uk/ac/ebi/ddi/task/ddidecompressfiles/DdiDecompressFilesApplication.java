package uk.ac.ebi.ddi.task.ddidecompressfiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.ebi.ddi.task.ddidecompressfiles.configuration.DecompressTaskProperties;
import uk.ac.ebi.ddi.task.ddidecompressfiles.service.DecompressFileService;

@SpringBootApplication
public class DdiDecompressFilesApplication implements CommandLineRunner {

	@Autowired
	private DecompressFileService decompressFileService;

	@Autowired
	private DecompressTaskProperties decompressTaskProperties;

	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(DdiDecompressFilesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		decompressFileService.decompress(decompressTaskProperties.getOriginalDirectory(),
				decompressTaskProperties.getTargetDirectory(), decompressTaskProperties.isDeleteOriginal());
	}
}
