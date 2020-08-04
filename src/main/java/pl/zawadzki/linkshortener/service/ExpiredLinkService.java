package pl.zawadzki.linkshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.repository.LinkRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpiredLinkService {
    private LinkRepository linkRepository;

    @Autowired
    public ExpiredLinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void checkExpiredLinkDate() {
        Optional<List<LinkRequest>> linkRequestList = linkRepository.findAllByExpireTimeBefore(LocalDateTime.now());
        if (linkRequestList.isPresent()) {
            linkRepository.deleteAll(linkRequestList.get());
        }
    }
}
