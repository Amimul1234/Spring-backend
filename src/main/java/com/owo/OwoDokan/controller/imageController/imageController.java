package com.owo.OwoDokan.controller.imageController;

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
    public String saveImageInProject(@PathVariable("directory") String directory, @RequestPart(name = "multipartFile") MultipartFile multipartFile)
    {
        String filename = UUID.randomUUID().toString() + multipartFile.getOriginalFilename();

        try {

            File dir = new File("images" + "/" + directory);

            if(!dir.exists())//Make Directory if not exists
                dir.mkdirs();

            Files.copy(multipartFile.getInputStream(), Paths.get(dir+ "/"+ filename), StandardCopyOption.REPLACE_EXISTING);

            return directory + "/" + filename;

        } catch (IOException e) {
            return "Failed to save image, Please try again";
        }

    }

    @GetMapping("/getImageFromServer")
    public byte[] sendImageToClient(@RequestParam(name = "path_of_image") String path_of_image)
    {

        byte[] requested_image;

        try {
            File file = new File("images" + "/" + path_of_image);//This is for determining byte array size
            requested_image = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file.getPath());
            fileInputStream.read(requested_image, 0, requested_image.length);
            fileInputStream.close();

            return requested_image;

        } catch (IOException e) {
            return null;
        }
    }
}
