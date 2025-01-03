package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.*;
import Service.SocialMediaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService mediaService;
    public SocialMediaController(){
        mediaService = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::userRegistrationHandler);
        app.post("/login", this::verifyLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::retrieveAllMessagesHandler);
        app.get("/messages/{message_id}", this::retrieveMessageIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessageUserHandler);
        return app;
    }

    private void userRegistrationHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        if(account.getUsername() == null || account.getPassword().length() < 4){
            ctx.status(400);
        }
        else{
            Account newAccount = mediaService.addAccount(account);
            if(newAccount==null){
                ctx.status(400);
            }else{
                ctx.json(mapper.writeValueAsString(newAccount));
            }
        }
        
    }

    private void verifyLoginHandler(Context ctx){

    }

    private void postMessageHandler(Context ctx){

    }

    private void retrieveAllMessagesHandler(Context ctx){

    }

    private void retrieveMessageIDHandler(Context ctx){

    }

    private void deleteMessageHandler(Context ctx){

    }

    private void updateMessageHandler(Context ctx){

    }

    private void retrieveAllMessageUserHandler(Context ctx){
        
    }
    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}