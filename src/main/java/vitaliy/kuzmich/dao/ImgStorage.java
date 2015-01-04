package vitaliy.kuzmich.dao;

import vitaliy.kuzmich.config.Const;
import vitaliy.kuzmich.excp.WrongFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/*@Component*/
public class ImgStorage {
    @Autowired
    HttpSession session;


    public List<String> getAllPics() {
        return Arrays.asList(createDirIfNExists().list());
    }

    public File toFile(MultipartFile file) {
        return new File(createDirIfNExists().getAbsolutePath() + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("_", "-"));
    }

    public File toFile(String fileName) {
        return new File(createDirIfNExists().getAbsolutePath() + "/" + fileName);
    }

    public byte[] toBytes(File image) {
        if (image.isFile() && image.exists()) {
            InputStream stream = null;
            try {
                stream = new FileInputStream(image);
                return IOUtils.toByteArray(stream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public boolean replace(File file, BufferedImage withImage) throws IOException, WrongFormatException {
        return ImageIO.write(withImage, "jpeg", file);
    }

    public File createDirIfNExists() {
        String sessionId = session.getId();
        File folder = new File(Const.PATH_UPLOADS + "/" + sessionId);
        if (!folder.exists()) {
            folder.mkdirs();
            folder.setWritable(true);
            folder.setReadable(true);
        }
        return folder;
    }

    public boolean clearUploads() {
        File f = new File(Const.PATH_UPLOADS);
        if (f.exists() && f.listFiles().length > 0)
            try {
                FileUtils.cleanDirectory(f);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }


        return false;
    }

}
