package app;

import app.config.HibernateConfig;
import app.controllers.PoemController;
import app.dto.PoemDTO;
import app.populators.PoemPopulator;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        PoemController poemController = new PoemController(emf);
        Javalin.create((config) ->
        {
            config.router.contextPath = "/api";
            config.router.apiBuilder(() -> ApiBuilder.path("poem", () ->
            {
                ApiBuilder.get("/", (ctx) -> ctx.json(poemController.getAll()));
                ApiBuilder.get("/{id}", (ctx) -> ctx.json(poemController.getById(Integer.parseInt(ctx.pathParam("id")))));
                ApiBuilder.post("/", (ctx) ->
                {
                    PoemDTO incomingPoem = (PoemDTO)ctx.bodyAsClass(PoemDTO.class);
                    poemController.create(incomingPoem);
                    ctx.json(incomingPoem);
                });
                ApiBuilder.put("/{id}",(ctx)->{
                    PoemDTO incomingPoem = (PoemDTO)ctx.bodyAsClass(PoemDTO.class);
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    poemController.setPoem(id, incomingPoem);
                    ctx.json(incomingPoem);
                });
            }));
        }).start(7070);


        //PoemPopulator.populate(emf); //Kør denne 1 gang for at populate databasen


        // Close the database connection:
        em.close();
        emf.close();
    }

}
