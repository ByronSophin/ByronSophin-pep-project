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
}
