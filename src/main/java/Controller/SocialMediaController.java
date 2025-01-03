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
        if(account.getUsername() == null || account.getUsername().isBlank() || account.getPassword().length() < 4){
            ctx.status(400);
        }
        else{
            Account newAccount = mediaService.addAccount(account);
            if(newAccount == null){
                ctx.status(400);
            }else{
                ctx.status(200).json(mapper.writeValueAsString(newAccount));
            }
        }
        
    }

    private void verifyLoginHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = mediaService.verifyAccount(account);
        if(newAccount == null){
            ctx.status(401);
        }else{
            ctx.status(200).json(mapper.writeValueAsString(newAccount));
        }
    }

    private void postMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        if(message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255){
            ctx.status(400);
        }
        else{
            Message newMessage = mediaService.addMessage(message);
            if(newMessage == null){
                ctx.status(400);
            }else{
                ctx.status(200).json(mapper.writeValueAsString(newMessage));
            }
        }
    }

    private void retrieveAllMessagesHandler(Context ctx){
        ctx.json(mediaService.getAllMessages());
    }

    private void retrieveMessageIDHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMessage = mediaService.getMessageID(messageID);
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }
        ctx.status(200);
    }

    private void deleteMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMessage = mediaService.deleteMessageByID(messageID);
        ctx.status(200);
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        String messageText = message.getMessage_text();
        if(messageText.length() > 255 || messageText == null || messageText.isBlank() || mediaService.getMessageID(messageID) == null){
            ctx.status(400);
        }
        else{
            Message newMessage = mediaService.updateMessage(messageID, messageText);
            if(newMessage == null){
                ctx.status(400);
            }
            ctx.status(200).json(mapper.writeValueAsString(newMessage));
        }
    }

    private void retrieveAllMessageUserHandler(Context ctx){
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(mediaService.getAllMessagesFromUser(accountID));
    }


}