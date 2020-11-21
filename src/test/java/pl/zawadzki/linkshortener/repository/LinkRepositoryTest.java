package pl.zawadzki.linkshortener.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for LinkRepository
 * */

@ExtendWith(SpringExtension.class)
@DataJpaTest
class LinkRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LinkRepository linkRepository;

    @Test
    public void whenFindByShortLink_thenReturnLinkRequest() {
        // Given
        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setShortLink("QWERTY");
        entityManager.persist(linkRequest);
        entityManager.flush();

        // When
        LinkRequest found = linkRepository.findByShortLink(linkRequest.getShortLink()).get();

        // Then
        assertThat(found.getShortLink()).isEqualTo(linkRequest.getShortLink());
    }

    @Test
    public void whenFindByShortLinkAndPassword_thenReturnLinkRequest() {
        // Given
        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setShortLink("QWERTY");
        linkRequest.setPassword(1234);
        entityManager.persist(linkRequest);
        entityManager.flush();

        // When
        LinkRequest found = linkRepository.findByShortLinkAndPassword(
                linkRequest.getShortLink(), linkRequest.getPassword()
        ).get();

        // Then
        assertThat(found).isEqualTo(linkRequest);
    }
}