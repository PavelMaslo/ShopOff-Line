/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.shopoffline.cms.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author user
 */
@Controller
@RequestMapping("/assets")
public class ResourcesController {
    
    @Value("#{stores['store.main']}")
    public String mainStore;
    
    @ResponseBody
    @RequestMapping(path = "/image/{name:[A-Za-z\\d-\\._]+}")
    public void getImage(HttpServletResponse resp, @PathVariable String name) throws IOException {
        Path findPath = Paths.get(mainStore, name); 
        
        System.out.printf("%s, %s\n", mainStore, findPath);
        
        if (Files.exists(findPath)) {
            try {
                Files.copy(findPath, resp.getOutputStream());
                resp.flushBuffer();
                return;
            } catch (IOException ex) {
                Logger.getLogger(ResourcesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        resp.sendError(404, "File not found.");
    }
    
}
