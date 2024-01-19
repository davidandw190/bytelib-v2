package bytelib.constants;

public class LibraryQuery {
    public static final String LOGIN_QUERY = "SELECT * FROM users WHERE (username = ? OR email = ?) LIMIT 1";
    public static final String REGISTRATION_QUERY = "INSERT INTO users (username, email, password, phone_no, role_id) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_USER_QUERY = "INSERT INTO users (username, password, email, phone_no, role_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SELECT_USER_TYPE_BY_ID_QUERY = "SELECT type_id FROM user_type WHERE name = ?";
    public static final String SELECT_USER_TYPE_BY_USER_ID_QUERY = "SELECT user_type.name FROM users JOIN user_type ON users.role_id = user_type.type_id WHERE user_id = ?";
    public static final String SELECT_USERS_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";
    public static final String SELECT_USERS_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String INSERT_LIBRARY_ITEM_QUERY = "INSERT INTO library_items (title, description, page_no, publisher, volume, edition, pub_date, genre_id, item_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_GENRE_BY_NAME_QUERY = "SELECT genre_id FROM book_genre WHERE display_name = ?";
    public static final String SELECT_PUBLISHING_INTERVAL_ID_BY_NAME_QUERY = "SELECT interval_id FROM publishing_intervals WHERE display_name = ?";
    public static final String INSERT_JOURNAL_QUERY = "INSERT INTO library_items (title, description, publisher, issue, page_no, citation_no, pub_date, topic_id, item_type_id, publishing_interval_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_ITEM_TYPE_ID_BY_NAME_QUERY = "SELECT type_id FROM item_type WHERE display_name = ?";
    public static final String SELECT_DOMAIN_ID_BY_DOMAIN_NAME_QUERY = "SELECT domain_id FROM research_domain WHERE display_name = ?";
    public static final String SELECT_USER_TYPE_ID_BY_TYPE_NAME_QUERY = "SELECT type_id FROM user_type WHERE display_name = ?";
    public static final String CHECK_LIB_ITEM_EXISTS_BY_TITLE_AND_TYPE_QUERY = "SELECT COUNT(*) FROM library_items WHERE title = ? AND item_type_id = ?";
    public static final String INSERT_ARTICLE_QUERY = "INSERT INTO library_items (title, description, page_no, citation_no, pub_date, topic_id, item_type_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_SCIENTIFIC_CATALOGUE_SORT_BY_NAME_QUERY = "SELECT * FROM library_items WHERE item_type_id IN (?, ?, ?) ORDER BY title";
    public static final String INSERT_TEXTBOOK_QUERY = "INSERT INTO library_items (title, description, page_no, publisher, edition, citation_no, pub_date, topic_id, item_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_ITEMS_AUTHORS_RELATIONSHIP_QUERY = "INSERT INTO library_item_authors (item_id, author_id) VALUES (?, ?)";
}
