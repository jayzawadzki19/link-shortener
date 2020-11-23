package pl.zawadzki.linkshortener.service;

import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;
    private static SecureRandom random = new SecureRandom();
    private static final String TEMPLATE_FOR_LINK = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";



    public LinkRequest createNewLink(Link link) {
        LinkRequest newLink = new LinkRequest();
        newLink.setFullLink(link.getLink());
        newLink.setShortLink(createShortLink());
        newLink.setPassword(generatePassword());
        return linkRepository.save(newLink);
    }

    public ResponseEntity redirectToSite(String key) {
        Optional<LinkRequest> value = linkRepository.findByShortLink(key);
        if (value.isPresent()) {
            String link = value.get().getFullLink();
            countSiteViews(value.get());
            return ResponseEntity.ok(new Link(link));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity deleteLink(String link, int password) {
        Optional<LinkRequest> linkRequest = linkRepository.findByShortLinkAndPassword(link, password);
        if (linkRequest.isPresent()) {
            linkRepository.delete(linkRequest.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.badRequest().header("Info", "Query with given link and password has not been found.").build();
    }

    /**
     * The createShortLink() method creates 6 character short link from template
     */
    private String createShortLink() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append(this.TEMPLATE_FOR_LINK.charAt(random.nextInt(this.TEMPLATE_FOR_LINK.length())));
        }
        return builder.toString();
    }

    /**
     * The countSiteViews() method updates number of site views from link
     */
    private void countSiteViews(LinkRequest linkRequest) {
        linkRequest.updateViews();
        linkRepository.save(linkRequest);
    }

    /**
     * The generatePassword() method generates password for confirming link remove
     */
    private int generatePassword() {
        return random.nextInt(9999);
    }

}
