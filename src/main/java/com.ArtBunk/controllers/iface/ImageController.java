package com.ArtBunk.controllers.iface;

import com.ArtBunk.classes.Image;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    public List<Image> getImages(@RequestParam(value="category",required=false, defaultValue = "painting") String category,@RequestParam(value="limit",required=false,defaultValue = "2") String limit,HttpServletResponse response);

    @RequestMapping(value="/images/{criteria}",method= RequestMethod.GET)
    @ResponseBody
    public List<Image> getImagesWithCriteria(@PathVariable("criteria") String criteria,@RequestParam(value="limit",required=false,defaultValue = "2") String limit,HttpServletResponse response);

    @RequestMapping(value="/image/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam(value="name",required=false) String name,
                                                 @RequestParam(value="file",required = false) MultipartFile file);

}
