package pl.zawadzki.linkshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.dto.Link;
import pl.zawadzki.linkshortener.repository.LinkRepository;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class LinkService {

    private LinkRepository linkRepository;
    private static SecureRandom random;
    private static final String TEMPLATE_FOR_LINK = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
        random = new SecureRandom();
    }


    public LinkRequest createNewLink(Link link){
        LinkRequest newLink = new LinkRequest();
        newLink.setFullLink(link.getLink());
        newLink.setShortLink(createShortLink());
        newLink.setPassword(generatePassword());
        return linkRepository.save(newLink);
    }

    public Link redirectToSite(String key){
        Optional<LinkRequest> value = linkRepository.findByShortLink(key);
        if (value.isPresent()){
            String link = value.get().getFullLink();
            return new Link(link);
        }
        return new Link("sd");
    }

    public ResponseEntity deleteLink(String link, int password){
        Optional<LinkRequest> linkRequest = linkRepository.findByShortLinkAndPassword(link,password);
        if (linkRequest.isPresent()){
            linkRepository.delete(linkRequest.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.badRequest().header("Info","Query with given link and password has not been found.").build();
    }

    private String createShortLink(){
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<6; i++){
            builder.append(this.TEMPLATE_FOR_LINK.charAt(random.nextInt(this.TEMPLATE_FOR_LINK.length())));
        }
        return builder.toString();
    }

    private int generatePassword(){
        return random.nextInt(999);
    }
}
