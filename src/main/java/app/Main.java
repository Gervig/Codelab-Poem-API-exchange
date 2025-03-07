package app;

import app.config.HibernateConfig;
import app.controllers.PoemController;
import app.dto.PoemDTO;
import app.populators.PoemPopulator;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import jakarta.persistence.EntityManagerFactory;

public class Main
{

    public static void main(String[] args)
    {
        // Create the EntityManagerFactory
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        //Uncomment this line if you want to populate the database once
        PoemPopulator.populate(emf);

        // Ensure that the PoemDAO instance is correctly initialized with the emf
        PoemController poemController = new PoemController(emf);

        // Initialize Javalin
        Javalin.create(config ->
        {
            config.router.contextPath = "/api";
            //TODO refactor this to a route package and create getRoutes methods
            config.router.apiBuilder(() -> ApiBuilder.path("poem", () ->
            {
                ApiBuilder.get("/", ctx -> ctx.json(poemController.getAll()));
                ApiBuilder.get("/{id}", ctx -> ctx.json(poemController.getById(Integer.parseInt(ctx.pathParam("id")))));
                ApiBuilder.post("/", ctx ->
                {
                    PoemDTO incomingPoem = ctx.bodyAsClass(PoemDTO.class);
                    poemController.create(incomingPoem);
                    ctx.json(incomingPoem);
                });
                ApiBuilder.put("/{id}", ctx ->
                {
                    PoemDTO incomingPoem = ctx.bodyAsClass(PoemDTO.class);
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    poemController.setPoem(id, incomingPoem);
                    ctx.json(incomingPoem);
                });
            }));
        }).start(7070);

        //TODO make routes for authors


        // Close the EntityManagerFactory when done
//        emf.close();
    }
}
