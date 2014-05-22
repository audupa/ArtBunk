package com.ArtBunk.controllers.iface;

import com.ArtBunk.classes.Image;
import com.ArtBunk.util.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @RequestMapping(value="/allImages",method= RequestMethod.GET)
    @ResponseBody
    public List<Image> getImages(@RequestParam(value="category",required=false, defaultValue = Constant.defaultImageCategory) String category,@RequestParam(value="limit",required=false,defaultValue = Constant.defaultImageLimit) String limit,HttpServletResponse response);

    @RequestMapping(value="/images/criteria/{criteria}",method= RequestMethod.GET)
    @ResponseBody
    public List<Image> getImagesWithCriteria(@PathVariable("criteria") String criteria,@RequestParam(value="limit",required=false,defaultValue = Constant.defaultImageLimit) String limit,HttpServletResponse response);

    @RequestMapping(value="/images/{id}",method= RequestMethod.GET)
    @ResponseBody
    public List<Image> getImageById(@PathVariable("id") String id,HttpServletResponse response);

    @RequestMapping(value="/image/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam(value="name", required = false) String name,@RequestParam(value="description",required = false) String description,@RequestParam(value="category",required = false) String category,@RequestParam(value="image_cost",required = false) String image_cost,@RequestParam(value="medium",required = false) String medium,@RequestParam(value="user_name",required = false) String user_name,@RequestParam(value="file",required = false) MultipartFile file);

    @RequestMapping(value="/img/{name}.{ext}", method=RequestMethod.GET)
    public ResponseEntity retrieveImage(@PathVariable("name") String name,@PathVariable("ext") String ext, HttpServletResponse response )throws IOException;

}
