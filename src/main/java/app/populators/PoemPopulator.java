package app.populators;

import app.dao.AuthorDAO;
import app.dao.PoemDAO;
import app.entities.Author;
import app.entities.Poem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PoemPopulator
{

    public static Poem[] populate(EntityManagerFactory emf)
    {
        PoemDAO poemDAO = PoemDAO.getInstance(emf);
        Author[] authors = AuthorPopulator.populate();
        Poem p1 = Poem.builder()
                .poem("There once was a fellow named Clyde,\n" +
                        "Who found a fast horse he could ride.\n" +
                        "But when it took flight,\n" +
                        "He held on so tight,\n" +
                        "That now he just glides far and wide.")
                .author(authors[0])
                .created(LocalDate.now())
                .build();

        Poem p2 = Poem.builder()
                .poem("A young girl who lived near the bay,\n" +
                        "Would sing to the fish every day.\n" +
                        "But one stormy night,\n" +
                        "She gave them a fright,\n" +
                        "And they all swam in terror away.")
                .author(authors[1])
                .created(LocalDate.now().minusMonths(5))
                .build();

        Poem p3 = Poem.builder()
                .poem("There once was a cat from Peru,\n" +
                        "Who danced in a pink tutu.\n" +
                        "It pranced and it twirled,\n" +
                        "With ribbons unfurled,\n" +
                        "And meowed, “I belong in a zoo!”")
                .author(authors[1])
                .created(LocalDate.now().minusYears(60))
                .build();

        Poem p4 = Poem.builder()
                .poem("A baker from London named Lou,\n" +
                        "Once slipped and fell into his stew.\n" +
                        "He climbed from the pot,\n" +
                        "Exclaiming, \"It's hot!\"\n" +
                        "And now he just sticks to fondue.")
                .author(authors[2])
                .created(LocalDate.now().minusDays(7))
                .build();

        Poem p5 = Poem.builder()
                .poem("A scientist working in space,\n" +
                        "Dropped his lunch—it just floated in place!\n" +
                        "He tried to retrieve,\n" +
                        "But had to believe,\n" +
                        "That tacos in zero-G chase.")
                .author(authors[2])
                .created(LocalDate.now().minusMonths(9))
                .build();

        Poem p6 = Poem.builder()
                .poem("A pirate who lived on the sea,\n" +
                        "Had a parrot who mimicked with glee.\n" +
                        "One day it did swear,\n" +
                        "Gave the captain a scare,\n" +
                        "Now it lives in a library, free.")
                .author(authors[2])
                .created(LocalDate.now().minusYears(40))
                .build();

        Poem p7 = Poem.builder()
                .poem("A penguin who loved to play ball,\n" +
                        "Was known as the best of them all.\n" +
                        "It kicked and it slid,\n" +
                        "Like no other did,\n" +
                        "And danced when it made the crowd call.")
                .author(authors[3])
                .created(LocalDate.now().minusDays(44))
                .build();
        Poem[] poems = new Poem[]{p1, p2, p3, p4, p5, p6, p7};
        for (Poem p : poems)
        {
            poemDAO.create(p);
        }
        return poems;
    }
}
