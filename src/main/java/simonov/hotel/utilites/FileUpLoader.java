package simonov.hotel.utilites;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUpLoader {
   public static void uploadImage(MultipartFile image, String path){
       if (!image.isEmpty()){
           try {
               File imageHotel = new File(path);
               image.transferTo(imageHotel);
           } catch (IOException e) {
               //TODO logging
           }
       }
   }
}
