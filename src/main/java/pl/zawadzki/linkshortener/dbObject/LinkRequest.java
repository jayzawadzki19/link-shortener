package pl.zawadzki.linkshortener.dbObject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class LinkRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String fullLink;
    @NotBlank
    private String shortLink;

    @NotBlank
    private String password;

    private int views = 0;

    @NotBlank
    private final LocalDateTime creationTime = LocalDateTime.now();

    @NotBlank
    private static final LocalDateTime expireTime = calculateExpiryDate();

    public void updateViews(){
        this.views = views + 1;
    }

    private static LocalDateTime calculateExpiryDate(){
     LocalDateTime dateTime =  LocalDateTime.now();
     return dateTime.plusMonths(1);
    }

}
