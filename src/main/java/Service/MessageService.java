package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     *
     * @param message an message object.
     * @return The persisted message if the persistence is successful.
     */
    public Message createMessageService(Message message) {
        return messageDAO.createMessage(message);
    }

    /**
     * @param none
     * @return List of all messages.
     */
    public List <Message> getAllMessagesService() {
        return messageDAO.getAllMessages();
    }

    /**
     * @param int message_id
     * @return the selected message
     */
    public Message getMessageByIdService(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    /**
     * @param int message_id
     * @return the deleted message
     */
    public Message deleteMessageByIdService(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    /**
     * @param int message_id
     * @param String message_text
     * @return the updated message
     */
    public Message updateMessageByIdService(int message_id, String message_text) {
        return messageDAO.UpdateMessageById(message_id, message_text);
    }

    /**
     * @param int message_id
     * @param String message_text
     * @return the updated message
     */
    public List <Message> getAllMessagesByAccountIdService(int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id);
    }
}
