package pl.zawadzki.linkshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.dto.Link;
import pl.zawadzki.linkshortener.dto.LinkAndPassword;
import pl.zawadzki.linkshortener.exception.LinkException;
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

    @GetMapping("/go/{link}")
    public void redirect(@PathVariable String link, HttpServletResponse httpServletResponse) {
        ResponseEntity<Link> redirectLink = linkService.redirectToSite(link);
        httpServletResponse.setHeader("Location", redirectLink.getBody().getLink());
        httpServletResponse.setStatus(302);
    }

    @PostMapping("/new")
    public LinkAndPassword saveNewLink(@RequestBody Link link) {
        LinkAndPassword linkAndPassword = new LinkAndPassword();
        if (checkLink(link)) {
            LinkRequest linkRequest = linkService.createNewLink(link);
            linkAndPassword.setShortLink(linkRequest.getShortLink());
            linkAndPassword.setPassword(linkRequest.getPassword());
        }
        return linkAndPassword;
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteLink(@RequestBody LinkAndPassword linkAndPassword) {
        return linkService.deleteLink(linkAndPassword.getShortLink(), linkAndPassword.getPassword());
    }

    @ExceptionHandler(LinkException.class)
    public ResponseEntity catchLinkException(LinkException exception) {
        return ResponseEntity.badRequest().header("Info", exception.getMessage()).build();
    }

    private boolean checkLink(Link link) {
        String linkExmpl = link.getLink();
        if (linkExmpl.isBlank()) {
            throw new LinkException("Link can not be empty.");
        }
        return true;
    }
}
