package Controller;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLogInAccountHandler);
        app.post("/messages", this::postCreateMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to post a new author.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Author object.
     * If AuthorService returns a null author (meaning posting an Author was unsuccessful), the API will return a 400
     * message (client error). There is no need to change anything in this method.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccountService(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void postLogInAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account logInAccount = accountService.logInService(account);
        if(logInAccount!=null){
            ctx.json(mapper.writeValueAsString(logInAccount));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }

    private void postCreateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessageService(message);
        if(newMessage!=null){
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessagesService();
        ctx.json(messages);
        ctx.status(200);

    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message message = messageService.getMessageByIdService(id);
        if (message != null)
            ctx.json(message);
        else
            ctx.json("");
        ctx.status(200);

    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message message = messageService.deleteMessageByIdService(id);
        if (message != null)
            ctx.json(message);
        else
            ctx.json("");
        ctx.status(200);

    }

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        
        Message newMessage = messageService.updateMessageByIdService(id, message.getMessage_text());
        if (newMessage != null){
            ctx.json(newMessage);
            ctx.status(200);
        }
        else
            ctx.status(400);
    }

    private void getAllMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("account_id")));

        List<Message> messages = messageService.getAllMessagesByAccountIdService(id);
        ctx.json(messages);
        ctx.status(200);

    }
}