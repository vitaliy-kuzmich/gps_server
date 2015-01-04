package vitaliy.kuzmich.controllers;

import vitaliy.kuzmich.config.Const;
import vitaliy.kuzmich.dao.ImgStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Uploader {

    @Autowired
    ImgStorage storage;


    @RequestMapping(value = Const.URL_UPLOAD_IMG)
    public String test(@RequestParam("file") MultipartFile[] file, @RequestParam("w") int w, @RequestParam("h") int h) throws Exception {

      /*  for (int i = 0; i < file.length; i++) {
            MultipartFile _file = file[i];
            BufferedImage imgTmp = ImageIO.read(_file.getInputStream());
            storage.replace(storage.toFile(_file), resizer.scale(imgTmp, w, h));
        }

*/
        return "accepted";
    }


    @RequestMapping(value = Const.URL_DOWNLOAD_IMG, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(HttpServletRequest req) {
        HttpStatus responseCode = HttpStatus.OK;
        String arr[] = req.getRequestURI().split("/");
        String fileName = arr[arr.length - 1];
        byte[] image = new byte[0];
        try {
            image = storage.toBytes(storage.toFile(fileName));
        } catch (Exception e) {
            responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(image, responseCode);
    }


}
