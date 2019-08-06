package uk.ac.ebi.ddi.task.ddidecompressfiles.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.*;

public final class FileUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static final String DOT = ".";
    public static final String ZIP_FILE_EXTENSION = "zip";
    public static final String GZIP_FILE_EXTENSION = "gz";
    public static final int BUFFER_SIZE = 1024;

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        return getFileExtension(fileName);
    }

    public static String getFileExtension(String fileName) {
        int mid = fileName.lastIndexOf(DOT);
        String fileNameExt = null;
        if (mid > 0) {
            fileNameExt = fileName.substring(mid + 1).toLowerCase();
        }

        return fileNameExt;
    }

    public static File unzip(File file, File targetDir) throws IOException {
        File decompressedFile = null;
        FileInputStream fileInputStream = new FileInputStream(file);
        try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                decompressedFile = new File(targetDir.getAbsolutePath() + File.separator + zipEntry.getName());

                //create directories if required.
                decompressedFile.getParentFile().mkdirs();

                if (!zipEntry.isDirectory()) {
                    int count;
                    byte[] buffer = new byte[BUFFER_SIZE];

//                    decompressedFile = new File(targetDir.getAbsolutePath() + File.separator + zipEntry.getName());
                    FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);

                    try (BufferedOutputStream stream = new BufferedOutputStream(fileOutputStream, BUFFER_SIZE)) {
                        while ((count = zipInputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                            stream.write(buffer, 0, count);
                            stream.flush();
                        }
                    }
                }
            }
        }

        return decompressedFile;
    }

    public static void deleteOriginalCompressedFile(File compressedFile, boolean deleteOriginal) {
        if (deleteOriginal && compressedFile.isFile()) {
            boolean deleted = FileUtils.deleteQuietly(compressedFile);
            if (!deleted) {
                String msg = "Failed to delete compressed file " + compressedFile.getAbsolutePath();
                LOGGER.error(msg);
            }
        }
    }

    /**
     * decompress gzip file
     */
    public static File gunzip(File file, File targetDir) throws IOException {
        File decompressedFile;
        FileInputStream fileInputStream = new FileInputStream(file);


        int count;
        byte[] buffer = new byte[BUFFER_SIZE];

        String newFileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        decompressedFile = new File(targetDir.getAbsolutePath() + File.separator + newFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);


        try (
                GZIPInputStream gzipInputStream = new GZIPInputStream(new BufferedInputStream(fileInputStream));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER_SIZE)
        ) {
            while ((count = gzipInputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                bufferedOutputStream.write(buffer, 0, count);
                bufferedOutputStream.flush();
            }
        }

        return decompressedFile;
    }
}
