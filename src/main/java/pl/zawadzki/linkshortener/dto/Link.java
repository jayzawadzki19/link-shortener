package pl.zawadzki.linkshortener.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Link {
    private String link;

    public Link(String link) {
        this.link = link;
    }
}
