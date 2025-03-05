package app.controllers;

import app.dao.AuthorDAO;
import app.dao.PoemDAO;
import app.dto.AuthorDTO;
import app.dto.PoemDTO;
import app.entities.Author;
import app.entities.Poem;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class PoemController
{
    EntityManagerFactory emf;
    private PoemDAO poemDAO;
    private AuthorDAO authorDAO;

    public PoemController(EntityManagerFactory _emf)
    {
        this.emf = _emf;
        this.poemDAO = PoemDAO.getInstance(emf);
        this.authorDAO = AuthorDAO.getInstance(emf);
    }

    public PoemDTO getById(int id) throws Exception
    {
        Poem poem = poemDAO.read(id);
        return new PoemDTO(poem);

    }

    public List<PoemDTO> getAll()
    {
        List<Poem> poems = poemDAO.readAll();
        List<PoemDTO> poemDTOList = new ArrayList<>();
        for (Poem p : poems)
        {
            PoemDTO poemDTO = new PoemDTO(p);
            poemDTOList.add(poemDTO);
        }
        return poemDTOList;
    }

    public PoemDTO setPoem(int id, PoemDTO poemDTO) throws Exception
    {
        Poem poem = poemDAO.read(id);

        if (poem == null)
        {
            throw new ApiException(401, "Error could not update poem with id " + id);
        }

        AuthorDTO authorDTO = poemDTO.getAuthorDTO();

        Author updatedAuthor = Author.builder()
                .name(authorDTO.getName())
                .age(authorDTO.getAge())
                .build();

        updatedAuthor = authorDAO.create(updatedAuthor);

        Poem updatedPoem = Poem.builder()
                .poem(poemDTO.getPoem())
                .author(updatedAuthor)
                .build();
        poemDAO.update(updatedPoem);

        return getById(id);
    }

    public PoemDTO create(PoemDTO poemDTO) throws Exception
    {
        AuthorDTO authorDTO = poemDTO.getAuthorDTO();
        Author author = Author.builder()
                .age(authorDTO.getAge())
                .name(authorDTO.getName())
                .build();

        author = authorDAO.create(author);

        Poem poem = Poem.builder()
                .poem(poemDTO.getPoem())
                .author(author)
                .created(poemDTO.getCreated())
                .build();
        poemDAO.create(poem);

        return getById(poem.getId());
    }

}
