package pl.zawadzki.linkshortener.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.dto.Link;
import pl.zawadzki.linkshortener.repository.LinkRepository;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @Mock
    private LinkRepository repository;

    @InjectMocks
    private LinkService service;



    @Test
    void shouldCreateNewLinkRequestFromLink() {
        //Given
        final Link link = new Link("testLink.com");
        final LinkRequest request = new LinkRequest();
        request.setFullLink(link.getLink());
        request.setShortLink("QWERT");
        request.setPassword(1234);

        //When
        Mockito.lenient().when(service.createNewLink(link)).thenReturn(request);

        //Then
        Assert.assertEquals(request.getFullLink(),link.getLink());
    }

}