package app.dao;

import app.entities.Author;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AuthorDAO implements IDAO<Author, Integer>
{
    private static EntityManagerFactory emf;
    private static AuthorDAO instance;

    public static AuthorDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new AuthorDAO();
        }
        return instance;
    }

    @Override
    public Author create(Author author)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                em.persist(author);
                em.getTransaction().commit();
                return author;
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating author", e);
            }
        }
    }

    @Override
    public Author read(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Author.class, id);
        }
    }

    @Override
    public List<Author> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT a FROM Author a ORDER BY a.id", Author.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of authors", e);
        }

    }

    @Override
    public Author update(Author author)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Author updatedAuthor = em.merge(author);
            em.getTransaction().commit();
            return updatedAuthor;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating author", e);
        }
    }

    @Override
    public void delete(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                Author author = em.find(Author.class, id);
                if (author == null)
                {
                    throw new NullPointerException();
                }
                em.getTransaction().begin();
                em.remove(author);
                em.getTransaction().commit();
            } catch (Exception e){
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting author", e);
            }
        }
    }
}
