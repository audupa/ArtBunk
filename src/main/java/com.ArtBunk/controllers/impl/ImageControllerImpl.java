package com.ArtBunk.controllers.impl;

import com.ArtBunk.classes.Image;
import com.ArtBunk.controllers.iface.ImageController;
import com.ArtBunk.dao.iface.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
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

    @Autowired
    private ImageDAO imageDAO;

    public List<Image> getImages(@RequestParam(value="category",required=false, defaultValue = "painting") String category,@RequestParam(value="limit",required=false,defaultValue = "2") String limit,HttpServletResponse response) {
        List<Image> images = null;
        images = imageDAO.getImages(category,limit);
        return images;
    }


    public List<Image> getImagesWithCriteria(@PathVariable("criteria")String criteria,@RequestParam(value="limit",required=false,defaultValue = "2") String limit,HttpServletResponse response){

        List<Image> images = null;
        images = imageDAO.getImagesWithCriteria(criteria,limit);
        return images;
    }


}
