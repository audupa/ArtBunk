package com.ArtBunk.dao.impl;

import com.ArtBunk.classes.Image;
import com.ArtBunk.dao.iface.ImageDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AUDUPA
 * Date: 12/27/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageDAOImpl extends BaseDAOImpl implements ImageDAO{

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
}
