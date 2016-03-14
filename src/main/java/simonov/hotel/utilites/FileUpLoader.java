package simonov.hotel.utilites;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUpLoader {
    private static Log log = LogFactory.getLog(FileUpLoader.class);

    public static void uploadImage(MultipartFile image, String path) {
        if (!image.isEmpty()) {
            try {
                File imageHotel = new File(path);
                image.transferTo(imageHotel);
            } catch (Exception e) {
                log.error("Image uploading failed due to exception:", e);
            }
        }
    }
}
