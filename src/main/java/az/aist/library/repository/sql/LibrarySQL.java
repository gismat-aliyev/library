package az.aist.library.repository.sql;

public class LibrarySQL {

    public static final String GET_BOOK_LIST = "select * from books b inner join book_status bs on b.status=bs.status_id";

    public static final String GET_BOOK_BY_ID = "select * from books b inner join book_status bs on b.status=bs.status_id where book_id=:id";

    public static final String GET_BOOK_BY_NAME = "select * from books b inner join book_status bs on b.status=bs.status_id where lower(book_name) like :name";

    public static final String ADD_BOOK = "insert into books(book_id, book_name, author, about, added_date, create_date_of_book, status)\n" +
            "values (default,:name,:author,:about,:add,:create,:status);";

    public static final String UPDATE_BOOK_STATUS = "update books set status=:status,author=:author," +
            "about=:about,create_date_of_book=:createDate where book_id=:id";

    public static final String MAIN_USER_QUERY = "select user_id,password, username,email,phone_number,full_name,date_of_birth,added_date, ur.role_name as role_name " +
            "from users u inner join user_role ur on u.role_id = ur.role_id ";

    public static final String GET_USER_LIST = MAIN_USER_QUERY +
            "where u.status = :status and u.role_id = :roleId";

    public static final String DELETE_USER = "update users set status = :status where user_id= :userId and role_id = 1";

    public static final String ADD_USER = "insert into users(user_id, username, password, email, phone_number, full_name, date_of_birth, added_date, status, role_id) \n" +
            "values(default,:username,:password,:email,:phone,:fullName,:dateOfBirth,sysdate(),1,1);";

    public static final String GET_USER_BY_ID = MAIN_USER_QUERY +
            "where u.status = 1 and u.role_id = 1 and user_id = :id";

    public static final String GET_USER_BY_USERNAME = MAIN_USER_QUERY +
            "where u.status = 1 and role_id=1 username like :username";

    public static final String GET_USER_BY_LOGIN = "select user_id, password, username,email,phone_number," +
            "full_name,date_of_birth,added_date, ur.role_name as role_name " +
            "from users u inner join user_role ur on u.role_id = ur.role_id " +
            "where u.status = 1 and username = :username";

    public static final String LOGIN_USER = MAIN_USER_QUERY +
            "where username=:username and password=:password and status=1";

    public static final String UPDATE_USER = "update users set username=:username,email=:email,phone_number=:phone,full_name=:fullName,date_of_birth=:dateOfBirth " +
            "where user_id=:userId";

    public static final String MAIN_TR_QUERY = "select tu.*,b.book_id as book_id, b.book_name as book_name, b.about as about,b.author as author from books b\n" +
            "    inner join (select tr_id, t.user_id as user_id, u.username as username,\n" +
            "       u.full_name as full_name,u.email as email,\n" +
            "       u.phone_number as phone_number,t.tr_date as tr_date,\n" +
            "       t.tr_status as tr_status, t.book_id as book_id\n" +
            "from transaction t inner join users u on t.user_id = u.user_id) tu on b.book_id=tu.book_id ";

    public static final String GET_TR_BY_USER_ID = MAIN_TR_QUERY + "where tr_status=:status and user_id=:userId";

    public static final String GET_TR_LIST = MAIN_TR_QUERY + "where tr_status=:status";

    public static final String GET_TR_INFO = MAIN_TR_QUERY + "where tu.tr_id=:trId";

    public static final String MAIN_UPDATE_TR = "update transaction set tr_status=:status where tr_id=:trId";

    public static final String ADD_TR = "insert into transaction(tr_id, book_id, user_id, tr_status,tr_date) VALUES (default,:bookId,:userId,1,sysdate())";

    public static final String GET_STATUS_LIST = "select * from book_status";

    public static final String DELETE_BOOK = "delete from books where book_id=:id";

    public static final String BOOK_STATUS_UPDATE = "update books set status=:status where book_id=:bookId";
}
