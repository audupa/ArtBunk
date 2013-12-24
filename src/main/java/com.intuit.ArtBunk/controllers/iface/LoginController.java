package com.intuit.ArtBunk.controllers.iface;

import com.intuit.ArtBunk.classes.Tweet;
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
 * Date: 12/14/12
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")

public interface LoginController {

    @RequestMapping(value="/twitter/users/showTweets",method= RequestMethod.GET)
    @ResponseBody
    public List<Tweet> showLoginPage(@QueryParam("userId") int userId,HttpServletResponse response);

}
