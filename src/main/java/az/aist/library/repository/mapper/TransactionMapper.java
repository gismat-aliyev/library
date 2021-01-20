package az.aist.library.repository.mapper;

import az.aist.library.model.Book;
import az.aist.library.model.Transactions;
import az.aist.library.model.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionMapper {

    public List<Transactions> getTransactionList(ResultSet rs) throws SQLException {
        List<Transactions> transactions = new ArrayList<>();
        while (rs.next()) {
            Transactions tr = new Transactions();
            Book book = new Book();
            User user = new User();
            tr.setTrId(rs.getLong("tr_id"));
            tr.setStatus(rs.getInt("tr_status"));
            tr.setTrDate(rs.getDate("tr_date"));
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            book.setBookId(rs.getLong("book_id"));
            book.setBookName(rs.getString("book_name"));
            book.setAbout(rs.getString("about"));
            book.setAuthor(rs.getString("author"));
            tr.setBooks(book);
            tr.setUser(user);
            transactions.add(tr);
        }
        return transactions;
    }

    public Transactions getTransaction(ResultSet rs) throws SQLException {
        Transactions tr = new Transactions();
        if (rs.next()) {
            Book book = new Book();
            User user = new User();
            tr.setTrId(rs.getLong("tr_id"));
            tr.setStatus(rs.getInt("tr_status"));
            tr.setTrDate(rs.getDate("tr_date"));
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            book.setBookId(rs.getLong("book_id"));
            book.setBookName(rs.getString("book_name"));
            book.setAbout(rs.getString("about"));
            book.setAuthor(rs.getString("author"));
            tr.setBooks(book);
            tr.setUser(user);
        }
        return tr;
    }
}
