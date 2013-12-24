package com.ArtBunk.controllers.impl;

import com.ArtBunk.classes.Image;
import com.ArtBunk.controllers.iface.ImageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
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

    public List<Image> getImages(@QueryParam("category") String category,HttpServletResponse response) {
        List<Image> images;
        try{
            images  = this.jdbcTemplate.query("Select * from image_repo where category='"+category+"'",
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
}
