package com.intuit.ArtBunk.controllers.impl;


import com.intuit.ArtBunk.classes.Tweet;
import com.intuit.ArtBunk.controllers.iface.LoginController;
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
 * Date: 9/7/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class LoginControllerImpl implements LoginController {

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    public  List<Tweet> showLoginPage(@QueryParam("userId") int userId,HttpServletResponse response) {
        List<Tweet> tweets;
        try{
            tweets  = this.jdbcTemplate.query("Select * from tweets where user_id="+userId ,
                    new BeanPropertyRowMapper(Tweet.class));
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
        if(tweets!=null)
            return tweets;
        else
            return null;
    }
}
