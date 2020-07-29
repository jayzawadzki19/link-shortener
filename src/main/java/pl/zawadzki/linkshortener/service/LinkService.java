package pl.zawadzki.linkshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.dto.Link;
import pl.zawadzki.linkshortener.repository.LinkRepository;

import java.security.SecureRandom;

@Service
public class LinkService {

    private LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    private static final String TEMPLATE_FOR_LINK = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public LinkRequest createNewLink(Link link){
        LinkRequest newLink = new LinkRequest();
        newLink.setFullLink(link.getLink());
        newLink.setShortLink(createShortLink());
        newLink.setPassword(generatePassword());
        return linkRepository.save(newLink);
    }

    private String createShortLink(){
        StringBuilder builder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i=0; i<5; i++){
            builder.append(this.TEMPLATE_FOR_LINK.charAt(random.nextInt(this.TEMPLATE_FOR_LINK.length())));
        }
        return builder.toString();
    }

    private int generatePassword(){
        SecureRandom random = new SecureRandom();
        return random.nextInt(4);
    }
}
