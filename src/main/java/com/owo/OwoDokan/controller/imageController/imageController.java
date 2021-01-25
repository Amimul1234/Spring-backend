package com.owo.OwoDokan.controller.imageController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
public class imageController {

    //This method is for saving upcoming image in filesystem
    @PostMapping("/imageController/{directory}")
    public ResponseEntity saveImageInProject(@PathVariable("directory") String directory, @RequestPart(name = "multipartFile") MultipartFile multipartFile)
    {
        String filename = UUID.randomUUID().toString() + multipartFile.getOriginalFilename();

        try {

            File dir = new File("images" + "/" + directory);

            if(!dir.exists())//Make Directory if not exists
                dir.mkdirs();

            Files.copy(multipartFile.getInputStream(), Paths.get(dir+ "/"+ filename), StandardCopyOption.REPLACE_EXISTING);


            String response = "/getImageFromServer?path_of_image=images/" + directory + "/" + filename;

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IOException e) {
            String failed = "Failed to save image, Please try again";

            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(failed);
        }

    }

    @GetMapping("/getImageFromServer")
    public ResponseEntity sendImageToClient(@RequestParam(name = "path_of_image") String path_of_image)
    {

        byte[] requested_image;

        try {
            File file = new File(path_of_image);//This is for determining byte array size
            requested_image = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file.getPath());
            fileInputStream.read(requested_image, 0, requested_image.length);
            fileInputStream.close();


            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(requested_image);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.FAILED_DEPENDENCY)
                    .body("No such image");
        }
    }

    @DeleteMapping("/getImageFromServer")
    public ResponseEntity deleteImage(@RequestParam(name = "path_of_image") String path_of_image)
    {
        File file = new File(path_of_image);

        if(file.exists())
        {
            try
            {
                file.delete();
                return ResponseEntity.status(HttpStatus.OK).body("Image Successfully Deleted");
            }
            catch (Exception e)
            {
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Failed To Delete Image");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Image does not exists");
        }
    }

}
