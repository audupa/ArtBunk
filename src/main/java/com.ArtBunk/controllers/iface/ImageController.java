package com.ArtBunk.controllers.iface;

import com.ArtBunk.classes.Image;
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

public interface ImageController {

    @RequestMapping(value="/images",method= RequestMethod.GET)
    @ResponseBody
    public List<Image> getImages(@QueryParam("category") String category,HttpServletResponse response);

}
