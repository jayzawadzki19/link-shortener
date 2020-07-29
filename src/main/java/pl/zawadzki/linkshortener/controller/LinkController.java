package pl.zawadzki.linkshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.dto.Link;
import pl.zawadzki.linkshortener.service.LinkService;

@RestController
@RequestMapping("/api")
public class LinkController {

    LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/new")
    public LinkRequest saveNewLink(@RequestBody Link link){
        return linkService.createNewLink(link);
    }
}
