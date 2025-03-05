package app.dto;
import app.entities.Author;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO
{
    private String name;
    private int age;

    public AuthorDTO (Author author){
        this.name = author.getName();
        this.age = author.getAge();
    }
}
