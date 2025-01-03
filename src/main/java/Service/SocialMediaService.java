package Service;

import Model.*;
import java.util.List;
import DAO.SocialMediaDAO;

public class SocialMediaService {
    SocialMediaDAO socialDAO;

    public SocialMediaService(){
        socialDAO = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO socialDAO){
        this.socialDAO = socialDAO;
    }

    public Account addAccount(Account account){
        return socialDAO.insertAccount(account);
    }

    public Account verifyAccount(Account account){
        return socialDAO.matchAccount(account);
    }

    public Message addMessage(Message message){
        if(socialDAO.getAccountByID(message.getPosted_by()) != null){
            return socialDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return socialDAO.selectAllMessages();
    }

    public Message getMessageID(int messageID){
        return socialDAO.getMessageByID(messageID);
    }

    public Message deleteMessageByID(int messageID){
        if(socialDAO.getMessageByID(messageID) != null){
            Message message = socialDAO.getMessageByID(messageID);
            socialDAO.deleteMessageByID(messageID);
            return message;
        }
        return null;
    }

    public Message updateMessage(int messageID, String messageText){
        if(socialDAO.getMessageByID(messageID) != null){
            socialDAO.updateMessageByID(messageID, messageText);
            return socialDAO.getMessageByID(messageID);
        }
        return null;
    }

    public List<Message> getAllMessagesFromUser(int accountID){
        return socialDAO.getAllMessagesFromUser(accountID);
    }

}
