package app.dto;

import app.entities.Poem;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoemDTO
{
    private String poem;
    private LocalDate created;

    public PoemDTO(Poem poem)
    {
    this.poem = poem.getPoem();
    this.created = poem.getCreated();
    }

}
