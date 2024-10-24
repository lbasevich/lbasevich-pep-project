package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;


public class MessageDAO {

    public Message createMessage(Message message){
    if (message.getMessage_text().length() > 0 && message.getMessage_text().length()<=255){
      Connection connection = ConnectionUtil.getConnection();
      
      try {
        //first query checks if posted_by variable in message is valid account_id
        String accountQuerySQL = "SELECT * FROM account WHERE account_id = ?";

        PreparedStatement accountQueryStatement = connection.prepareStatement(accountQuerySQL);

        accountQueryStatement.setInt(1, message.getPosted_by());

        ResultSet accountRS = accountQueryStatement.executeQuery();

        Account resultAccount = null;
        while(accountRS.next()){
          resultAccount = new Account(accountRS.getInt("account_id"),
          accountRS.getString("username"),
          accountRS.getString("password"));
      }
        // System.out.println("result account id: " + resultAccount.getAccount_id());
        // System.out.println("message account id: " + message.getPosted_by());

        if (resultAccount != null && resultAccount.getAccount_id() == message.getPosted_by()){
          String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
          PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

          preparedStatement.setInt(1, message.getPosted_by());
          preparedStatement.setString(2, message.getMessage_text());
          preparedStatement.setLong(3, message.getTime_posted_epoch());

          preparedStatement.executeUpdate();

          ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
          if(pkeyResultSet.next()){
              int generated_message_id = (int) pkeyResultSet.getLong(1);
              return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(),
              message.getTime_posted_epoch());
          }
        }   
     }

      catch(SQLException e){
        System.out.println(e.getMessage());
      }
    }
    return null;
  }

  public List <Message> getAllMessages(){
      Connection connection = ConnectionUtil.getConnection();
      
      List<Message> messages = new ArrayList<>();

      try {
        String sql = "SELECT * FROM message";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
          Message message = new Message (rs.getInt("message_id"),
                  rs.getInt("posted_by"),
                  rs.getString("message_text"),
                  rs.getLong("time_posted_epoch"));
          messages.add(message);
      }
 
     }

      catch(SQLException e){
        System.out.println(e.getMessage());
      }
  
    return messages;
  }

  public Message getMessageById(int message_id){
    Connection connection = ConnectionUtil.getConnection();
    
    try {
      String sql = "SELECT * FROM message WHERE message_id = ?";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1, message_id);;

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){
        Message message = new Message (rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return message;
    }

   }

    catch(SQLException e){
      System.out.println(e.getMessage());
    }

  return null;
}

public Message deleteMessageById(int message_id){
  Connection connection = ConnectionUtil.getConnection();
  
  try {
    String messageQuerySQL = "SELECT * FROM message WHERE message_id = ?";

    PreparedStatement messageQueryStatement = connection.prepareStatement(messageQuerySQL);

    messageQueryStatement.setInt(1, message_id);;

    ResultSet messageQueryRS = messageQueryStatement.executeQuery();

    String sql = "DELETE FROM message WHERE message_id = ?";

    PreparedStatement preparedStatement = connection.prepareStatement(sql);

    preparedStatement.setInt(1, message_id);;

    preparedStatement.executeUpdate();

    while(messageQueryRS.next()){
      Message message = new Message (messageQueryRS.getInt("message_id"),
      messageQueryRS.getInt("posted_by"),
      messageQueryRS.getString("message_text"),
      messageQueryRS.getLong("time_posted_epoch"));
      return message;
  }

 }

  catch(SQLException e){
    System.out.println(e.getMessage());
  }

return null;
}

public Message UpdateMessageById(int message_id, String message_text){
  Connection connection = ConnectionUtil.getConnection();
  if (message_text.length() > 0 && message_text.length()<=255){

  try {
    String messageUpdateQuerySQL = "UPDATE message SET message_text = ? WHERE message_id = ?";

    PreparedStatement messageUpdateStatement = connection.prepareStatement(messageUpdateQuerySQL);

    messageUpdateStatement.setString(1, message_text);
    messageUpdateStatement.setInt(2, message_id);;

    messageUpdateStatement.executeUpdate();

    String sql = "SELECT * FROM message WHERE message_id = ?";

    PreparedStatement preparedStatement = connection.prepareStatement(sql);

    preparedStatement.setInt(1, message_id);;

    ResultSet messageQueryRS = preparedStatement.executeQuery();

    while(messageQueryRS.next()){
      Message message = new Message (messageQueryRS.getInt("message_id"),
      messageQueryRS.getInt("posted_by"),
      messageQueryRS.getString("message_text"),
      messageQueryRS.getLong("time_posted_epoch"));
      return message;
  }

 }

  catch(SQLException e){
    System.out.println(e.getMessage());
  }
  }
return null;
}

public List <Message> getAllMessagesByAccountId(int account_id){
  Connection connection = ConnectionUtil.getConnection();
  
  List<Message> messages = new ArrayList<>();

  try {
    String sql = "SELECT * FROM message WHERE posted_by = ?";

    PreparedStatement preparedStatement = connection.prepareStatement(sql);

    preparedStatement.setInt(1, account_id);;

    ResultSet rs = preparedStatement.executeQuery();

    while(rs.next()){
      Message message = new Message (rs.getInt("message_id"),
              rs.getInt("posted_by"),
              rs.getString("message_text"),
              rs.getLong("time_posted_epoch"));
      messages.add(message);
  }

 }

  catch(SQLException e){
    System.out.println(e.getMessage());
  }

return messages;
}

}
