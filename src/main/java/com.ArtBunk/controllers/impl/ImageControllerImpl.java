package com.ArtBunk.controllers.impl;

import com.ArtBunk.classes.Image;
import com.ArtBunk.classes.ImageFile;
import com.ArtBunk.controllers.iface.ImageController;
import com.ArtBunk.dao.iface.ImageDAO;
import com.ArtBunk.util.Constant;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AUDUPA
 * Date: 12/24/13
 * Time: 11:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class ImageControllerImpl implements ImageController {
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private ImageDAO imageDAO;

    public List<Image> getImages(@RequestParam(value="category",required=false, defaultValue = Constant.defaultImageCategory) String category,@RequestParam(value="limit",required=false,defaultValue = Constant.defaultImageLimit) String limit,HttpServletResponse response) {
        List<Image> images = null;
        images = imageDAO.getImages(category,limit);
        return images;
    }


    public List<Image> getImagesWithCriteria(@PathVariable("criteria")String criteria,@RequestParam(value="limit",required=false,defaultValue = Constant.defaultImageLimit) String limit,HttpServletResponse response){

        List<Image> images = null;
        images = imageDAO.getImagesWithCriteria(criteria,limit);
        return images;
    }

    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                imageDAO.saveImage(file);
                return "You successfully uploaded!";
            } catch (Exception e) {
                return "You failed to upload" + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }

    public ResponseEntity retrieveImage(@PathVariable("name") String name, @PathVariable("ext") String ext, HttpServletResponse response) throws IOException {
        String name1 = name + "." + ext;
        OutputStream out = response.getOutputStream();
        List<ImageFile> imageFile = imageDAO.retrieveImage(name1, response);
        if(!imageFile.isEmpty()) {
            response.setContentType(imageFile.get(0).getContentType());
            byte[] image = imageFile.get(0).getContent();
            out.write(image);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
