package app;

import app.config.HibernateConfig;
import app.populators.PoemPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();


        PoemPopulator.populate(emf);


        // Close the database connection:
        em.close();
        emf.close();
    }

}
