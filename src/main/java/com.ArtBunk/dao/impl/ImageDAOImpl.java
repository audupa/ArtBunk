package com.ArtBunk.dao.impl;

import com.ArtBunk.classes.Image;
import com.ArtBunk.classes.ImageFile;
import com.ArtBunk.dao.iface.ImageDAO;
import com.ArtBunk.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
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
        return images;

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

        return images;

    }

    //show all the images depending on a criteria and limit being optional

    public Image getImageById(String id){
        List<Image> image = null;

        try{
            image  = this.jdbcTemplate.query("Select * from image_repo where id=" +id,
                    new BeanPropertyRowMapper(Image.class));
            return image.get(0);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    public boolean saveImage(MultipartFile file,Image image) throws IOException {
        ImageFile imageFile = null;
        if(!file.isEmpty()) {

            /*byte[] bytes = file.getBytes();

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("-uploaded")));
            stream.write(bytes);
            stream.close();

            String filePath = "C:/Users/audupa/ArtBunk/src/main/webapp/images/" + file.getOriginalFilename(); //Please note that I am going to remove hardcoaded path to get it from resource/property file
            File dest = new File(filePath);
            file.transferTo(dest);*/

            //Store the image in DB
            imageFile = new ImageFile();
            imageFile.setFileSize(file.getSize());
            imageFile.setFileName(file.getOriginalFilename());
            imageFile.setContent(file.getBytes());
            imageFile.setContentType(file.getContentType());

            long id = new SimpleJdbcInsert(dataSource).
                    withTableName("images").
                    usingColumns("file_size", "file_name", "content", "content_type" ).
                    usingGeneratedKeyColumns("id").
                    executeAndReturnKey(new BeanPropertySqlParameterSource(imageFile)).longValue();

            System.out.println("************************"+id);

            String image_url = Constant.imageUrlPrefix + file.getOriginalFilename();
            // update the image_repo to store this id add an entry in the image_repo table
            String SQL = "insert into image_repo (image_url,date_created,last_updated,name,description,category,image_cost,medium,user_name) values (?,now(),now(),?,?,?,?,?,?)";

            jdbcTemplate.update(SQL,image_url,image.getUserName(),image.getDescription(),image.getCategory(),image.getImageCost(),image.getMedium(),image.getUserName());


        }
        return true;
    }


    public List<ImageFile> retrieveImage(String name,HttpServletResponse response){

        List<ImageFile> imageFile = null;

        try{
              imageFile =  this.jdbcTemplate.query("Select * from images where file_name ='"+name+"'",
                    new BeanPropertyRowMapper(ImageFile.class));

              return imageFile;
        }
        catch (EmptyResultDataAccessException e) {
            return imageFile;
        }
    }
}
