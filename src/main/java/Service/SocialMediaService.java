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
}
