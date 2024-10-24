package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

  public Account createAccount(Account account){
    if (account.getUsername().length()>1 && account.getPassword().length() >= 4){
      Connection connection = ConnectionUtil.getConnection();
      try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());

        preparedStatement.executeUpdate();
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generated_account_id = (int) pkeyResultSet.getLong(1);
            return new Account(generated_account_id, account.getUsername(), account.getPassword());
        }
      }catch(SQLException e){
        System.out.println(e.getMessage());
      }
    }
    return null;
  }
    

  public Account logIn(Account account){
      Connection connection = ConnectionUtil.getConnection();
      try {
         String sql = "SELECT * FROM account WHERE username = ?" ;
         PreparedStatement preparedStatement = connection.prepareStatement(sql);

         preparedStatement.setString(1, account.getUsername());

         ResultSet rs = preparedStatement.executeQuery();

         Account resultAccount = null;
        while(rs.next()){
          resultAccount = new Account(rs.getInt("account_id"),
                  rs.getString("username"),
                  rs.getString("password"));
      }
    
      //  if account is retreived and passwords match, login account is returned
       if (resultAccount != null && resultAccount.getPassword().equals(account.getPassword())){
        return resultAccount;
       }


      }catch(SQLException e){
        System.out.println(e.getMessage());
      }

      return null;
  }
}
