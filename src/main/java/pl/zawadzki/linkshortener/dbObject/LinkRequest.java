package pl.zawadzki.linkshortener.dbObject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class LinkRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullLink;

    private String shortLink;

    private int password;

    private int views = 0;

    private final LocalDateTime creationTime = LocalDateTime.now();

    private final LocalDateTime expireTime = calculateExpiryDate();

    public void updateViews(){
        this.views = views + 1;
    }

    private static LocalDateTime calculateExpiryDate(){
     LocalDateTime dateTime =  LocalDateTime.now();
     return dateTime.plusMonths(1);
    }

}
