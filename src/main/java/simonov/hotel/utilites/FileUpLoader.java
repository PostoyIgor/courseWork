package simonov.hotel.utilites;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUpLoader {
    private static Log log = LogFactory.getLog(FileUpLoader.class);

    public static void uploadImage(MultipartFile image, String path) {
        if (!image.isEmpty()) {
            try {
                File imagePath = new File(path);
                image.transferTo(imagePath);
            } catch (IOException | IllegalStateException e) {
                log.error("Image uploading failed due to exception:", e);
            }
        }
    }
}
