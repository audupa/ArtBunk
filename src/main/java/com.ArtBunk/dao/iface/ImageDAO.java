package com.ArtBunk.dao.iface;

import com.ArtBunk.classes.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AUDUPA
 * Date: 12/27/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ImageDAO {

    //show all the images (category and limit being optional)
    public List<Image> getImages(String category, String limit);
    //show all the images depending on a criteria , limit being optional
    public List<Image> getImagesWithCriteria(String criteria, String limit);

    public boolean saveImage(MultipartFile file) throws IOException;
}
