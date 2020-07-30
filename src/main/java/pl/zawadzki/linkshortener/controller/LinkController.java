package pl.zawadzki.linkshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.dto.Link;
import pl.zawadzki.linkshortener.dto.LinkAndPassword;
import pl.zawadzki.linkshortener.service.LinkService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class LinkController {

    LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{link}")
    public void redirect(@PathVariable String link, HttpServletResponse httpServletResponse){
        Link redirectLink = linkService.redirectToSite(link);
        httpServletResponse.setHeader("Location", redirectLink.getLink());
        httpServletResponse.setStatus(302);
    }

    @PostMapping("/new")
    public LinkAndPassword saveNewLink(@RequestBody Link link){
        LinkRequest linkRequest = linkService.createNewLink(link);
        LinkAndPassword linkAndPassword = new LinkAndPassword();
        linkAndPassword.setLink(linkRequest.getShortLink());
        linkAndPassword.setPassword(linkRequest.getPassword());
        return linkAndPassword;
    }

    @DeleteMapping("/delete")
    public void deleteLink(@RequestBody LinkAndPassword linkAndPassword){
        linkService.deleteLink(linkAndPassword.getLink(),linkAndPassword.getPassword());
    }
}
