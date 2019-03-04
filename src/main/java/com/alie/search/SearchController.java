package com.alie.search;

import com.alie.search.assets.ProjeOdev;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SearchController {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HashMap<String, Integer> getData(@RequestParam String keyw){
        return  new ProjeOdev().run(keyw);
    }
}
