package pl.zawadzki.linkshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<LinkRequest, Long> {

    Optional<LinkRequest> findByShortLinkAndPassword(String shortLink, int password);

    Optional<LinkRequest> findByShortLink(String shortLink);

    Optional<List<LinkRequest>> findAllByExpireTimeBefore(LocalDateTime expireDate);

    //Optional<List<LinkRequest>> findAllByBeforeTimeAfter(LocalDateTime expireTime);
}
