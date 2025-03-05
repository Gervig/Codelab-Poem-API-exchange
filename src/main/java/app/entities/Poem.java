package app.entities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@DynamicUpdate
@Entity

public class Poem
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String poem;
    private LocalDate created;

    @Setter
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

}
