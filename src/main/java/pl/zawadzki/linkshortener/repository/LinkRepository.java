package pl.zawadzki.linkshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<LinkRequest, Long> {
    Optional<LinkRequest> findByPassword(String password);

    Optional<LinkRequest> findByShortLink(String shortLink);
}
