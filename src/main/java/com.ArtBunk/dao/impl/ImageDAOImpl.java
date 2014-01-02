package com.ArtBunk.dao.impl;

import com.ArtBunk.classes.Image;
import com.ArtBunk.classes.ImageFile;
import com.ArtBunk.dao.iface.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AUDUPA
 * Date: 12/27/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageDAOImpl extends BaseDAOImpl implements ImageDAO{

    @Autowired
    DataSource dataSource;
    //show all the images (category and limit being optional)
    public List<Image> getImages(String category,String limit){
        List<Image> images = null;
        try{
            images  = this.jdbcTemplate.query("Select * from image_repo where category='"+category+"' limit" +" "+limit,
                    new BeanPropertyRowMapper(Image.class));
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
        if(images!=null)
            return images;
        else
            return null;

    }

    //show all the images depending on a criteria and limit being optional

    public List<Image> getImagesWithCriteria(String criteria, String limit){
        List<Image> images = null;

        if(criteria.equals("popular")) {
            try{
                images  = this.jdbcTemplate.query("Select * from image_repo order by no_of_likes desc limit"+" "+limit,
                        new BeanPropertyRowMapper(Image.class));
            }
            catch (EmptyResultDataAccessException e) {
                return null;
            }
        }

        if(criteria.equals("recent")) {
            try{
                images  = this.jdbcTemplate.query("Select * from image_repo order by date_created desc limit"+" "+limit,
                        new BeanPropertyRowMapper(Image.class));
            }
            catch (EmptyResultDataAccessException e) {
                return null;
            }
        }

        if(images!=null)
            return images;
        else
            return null;

    }


    public boolean saveImage(MultipartFile file) throws IOException {
        ImageFile imageFile = null;
        if(!file.isEmpty()) {

            byte[] bytes = file.getBytes();

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("-uploaded")));
            stream.write(bytes);
            stream.close();

            String filePath = "C:/Users/audupa/ArtBunk/src/main/webapp/images/" + file.getOriginalFilename(); //Please note that I am going to remove hardcoaded path to get it from resource/property file
            File dest = new File(filePath);
            file.transferTo(dest);

            //Store the image in DB
           /* imageFile = new ImageFile();
            imageFile.setFileSize(file.getSize());
            imageFile.setFileName(file.getOriginalFilename());
            imageFile.setContent(file.getBytes());
            imageFile.setContentType(file.getContentType());

            long id = new SimpleJdbcInsert(dataSource).
                    withTableName("images").
                    usingColumns("file_size", "file_name", "content", "content_type" ).
                    usingGeneratedKeyColumns("id").
                    executeAndReturnKey(new BeanPropertySqlParameterSource(imageFile)).longValue();

            System.out.println("************************"+id);  */

            // update the image_repo to store this id

        }
        return true;
    }
}
