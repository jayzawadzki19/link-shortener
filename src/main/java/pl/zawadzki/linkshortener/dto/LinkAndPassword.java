package pl.zawadzki.linkshortener.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkAndPassword {

    private String shortLink;
    private int password;

}
