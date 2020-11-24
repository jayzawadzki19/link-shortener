package pl.zawadzki.linkshortener.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zawadzki.linkshortener.dbObject.LinkRequest;
import pl.zawadzki.linkshortener.dto.Link;
import pl.zawadzki.linkshortener.exception.LinkException;
import pl.zawadzki.linkshortener.service.LinkService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LinkControllerTest {

    @Mock
    private LinkService service;

    @InjectMocks
    private LinkController controller;

    @Test
    public void shouldThrowLinkExceptionWhenLinkIsEmpty() {
        //Given
        final Link link = new Link("");
        Mockito.lenient().when(service.createNewLink(link)).thenReturn(new LinkRequest());

        //When
        assertThrows(LinkException.class,() -> {
            controller.saveNewLink(link);
        });

        //Then
        verify(service,never()).createNewLink(any(Link.class));
    }


}