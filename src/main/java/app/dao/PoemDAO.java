package app.dao;

import app.entities.Author;
import app.entities.Poem;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PoemDAO implements IDAO<Poem, Integer>
{
    private static EntityManagerFactory emf;
    private static PoemDAO instance;

    public PoemDAO(){}

    public static PoemDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new PoemDAO();
        }
        return instance;
    }

    @Override
    public Poem create(Poem poem)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                Author author = poem.getAuthor();

                Author exsistingAuhtor = em.find(Author.class, author.getId());
                if (exsistingAuhtor == null)
                {
                    em.persist(author);
                } else {
                    poem.setAuthor(exsistingAuhtor);
                }

                em.persist(poem);
                em.getTransaction().commit();
                return poem;
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating poem", e);
            }
        }
    }

    @Override
    public Poem read(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Poem.class, id);
        }
    }

    @Override
    public List<Poem> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p FROM Poem p ORDER BY p.id", Poem.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of poem", e);
        }

    }

    @Override
    public Poem update(Poem poem)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Poem updatedPoem = em.merge(poem);
            em.getTransaction().commit();
            return updatedPoem;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating Poem", e);
        }
    }

    @Override
    public void delete(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                Poem poem = em.find(Poem.class, id);
                if (poem == null)
                {
                    throw new NullPointerException();
                }
                em.getTransaction().begin();
                em.remove(poem);
                em.getTransaction().commit();
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting Poem", e);
            }
        }
    }
}
