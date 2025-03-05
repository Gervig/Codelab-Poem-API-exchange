package app.populators;

import app.entities.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorPopulator
{
    public static Author[] populate()
    {

        Author a1 = Author.builder()
                .age(23)
                .name("Timmy")
                .build();

        Author a2 = Author.builder()
                .age(90)
                .name("Bobby")
                .build();

        Author a3 = Author.builder()
                .age(56)
                .name("Kim")
                .build();
        Author a4 = Author.builder()
                .age(31)
                .name("Gertrude")
                .build();

        return new Author[]{a1, a2, a3, a4};
    }
}
