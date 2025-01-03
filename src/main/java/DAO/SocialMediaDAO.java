package DAO;

import Model.*;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaDAO {
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStatement.setString(1, account.getUsername());
            prepStatement.setString(2, account.getPassword());
            prepStatement.executeUpdate();
            ResultSet resultKey = prepStatement.getGeneratedKeys();
            if(resultKey.next()){
                int newAccountID = (int) resultKey.getLong(1);
                return new Account(newAccountID, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Account matchAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, account.getUsername());
            prepStatement.setString(2, account.getPassword());
            ResultSet result = prepStatement.executeQuery();
            if(result.next()){
                return new Account(result.getInt("account_id"), account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByID(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setInt(1, accountID);
            ResultSet result = prepStatement.executeQuery();
            if(result.next()){
                return new Account(result.getInt("account_id"), result.getString("username"), result.getString("password"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStatement.setInt(1, message.getPosted_by());
            prepStatement.setString(2, message.getMessage_text());
            prepStatement.setLong(3, message.getTime_posted_epoch());
            prepStatement.executeUpdate();
            ResultSet resultKey = prepStatement.getGeneratedKeys();
            if(resultKey.next()){
                int newMessageID = (int) resultKey.getLong(1);
                return new Message(newMessageID, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> selectAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message;";

            PreparedStatement prepStatement = connection.prepareStatement(sql);
            ResultSet result = prepStatement.executeQuery();
            while(result.next()){
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageByID(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setInt(1, messageID);
            ResultSet result = prepStatement.executeQuery();
            if(result.next()){
                return new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMessageByID(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setInt(1, messageID);
            prepStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateMessageByID(int messageID, String messageText){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, messageText);
            prepStatement.setInt(2, messageID);
            prepStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Message> getAllMessagesFromUser(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setInt(1, accountID);
            ResultSet result = prepStatement.executeQuery();
            while(result.next()){
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
