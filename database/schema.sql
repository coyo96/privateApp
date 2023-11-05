BEGIN TRANSACTION;

CREATE TABLE users (
    user_id varchar(36) NOT NULL, --This will be a UUID generated before insertion
    username varchar(20) NOT NULL UNIQUE,
    first_name varchar(30) NOT NULL,
    middle_name varChar(30),
    last_name varChar(30) NOT NULL,
    email varChar(100) NOT NULL UNIQUE,
    password_hash varchar(200) NOT NULL,
    password_salt varChar(20) NOT NULL,
    role varChar(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    primary_phone int NOT NULL UNIQUE,
    confirmed boolean DEFAULT FALSE,
    confirmed_on timestampz, --TIMESTAMPZ is a postgres keyword that is a date and time with time zone.
    created_on timestampz DEFAULT now(),
    gender_code varChar(1), --Optional parameter.
    activated boolean DEFAULT TRUE, --If a user deletes their account, it is bad practice to actually delete it. This marks that the account should not be visible to anyone.
    CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE categories (
    category_id SERIAL,
    category_name varChar(50) NOT NULL,
    CONSTRAINT PK_categories PRIMARY KEY (category_id)
)

CREATE TABLE events (
    event_id SERIAL,
    organizer_id varChar(36) NOT NULL,
    event_name varChar(100) NOT NULL,
    event_date DATE NOT NULL,
    event_time TIME WITH TIME ZONE NOT NULL,
    description varChar(1000) NOT NULL,
    img_location varChar(200) NOT NULL, --This will be the URI for the images stored in a separate location.
    ticket_cost NUMERIC(2),
    purchase_ticket_link varChar(200),
    created_on timestampz DEFAULT now(),
    is_private boolean DEFAULT FALSE,
    CONSTRAINT PK_event PRIMARY KEY (event_id),
    CONSTRAINT FK_organizer_id FOREIGN KEY (organizer_id) REFERENCES users(user_id)

);

CREATE TABLE event_categories (
    event_id int NOT NULL,
    category_id int NOT NULL,
    CONSTRAINT PK_event_category PRIMARY KEY (event_id, category_id),
    CONSTRAINT FK_event_id FOREIGN KEY (event_id) REFERENCES events(event_id),
    CONSTRAINT FK_category_id FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE locations (
    location_id SERIAL,
    location_name varChar(150) NOT NULL,
    address varChar(150) NOT NULL,
    address_line_2 varChar(150),
    city varChar(50) NOT NULL,
    state_province varChar(50) NOT NULL,
    postal_code int NOT NULL,
    country varChar(75),
    CONSTRAINT PK_location PRIMARY KEY (location_id)
);

CREATE TABLE user_address (
    user_id varChar(36) NOT NULL,
    location_id int NOT NULL,
    is_default boolean NOT NULL,
    CONSTRAINT PK_user_address PRIMARY KEY (user_id, location_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_location_id FOREIGN KEY (location_id) REFERENCES locations(location_id)
);

CREATE TABLE event_location (
    event_id int NOT NULL,
    location_id int NOT NULL,
    CONSTRAINT PK_event_location PRIMARY KEY (event_id, location_id),
    CONSTRAINT FK_event_id FOREIGN KEY (event_id) REFERENCES events(event_id),
    CONSTRAINT FK_location_id FOREIGN KEY (location_id) REFERENCES locations(location_id)
);

CREATE TABLE tags (
    tag_id SERIAL,
    name varChar(25) UNIQUE NOT NULL,
    CONSTRAINT PK_tags PRIMARY KEY (tag_id)
);

CREATE TABLE event_tags (
    event_id int NOT NULL,
    tag_id int NOT NULL,
    CONSTRAINT PK_event_tags PRIMARY KEY (event_id, tag_id), --COMPOSITE KEY
    CONSTRAINT FK_event_id FOREIGN KEY (event_id) REFERENCES events(event_id),
    CONSTRAINT FK_tag_id FOREIGN KEY (tag_id) REFERENCES tags(tag_id)
);

CREATE TABLE reviews (
    review_id SERIAL,
    reviewer_id varChar(36) NOT NULL,
    event_id int NOT NULL,
    review_description varChar(1000) NOT NULL,
    rating int NOT NULL,
    created_on timestampz DEFAULT now(),
    CONSTRAINT PK_reviews PRIMARY KEY (review_id),
    CONSTRAINT FK_reviewer_id FOREIGN KEY (reviewer_id) REFERENCES users(user_id),
    CONSTRAINT FK_event_id FOREIGN KEY (event_id) REFERENCES events(event_id),
    CONSTRAINT CHK_valid_rating CHECK (rating > 0 AND rating < 6) --Rating 1-5 Stars
);

CREATE TABLE user_following (
    follower_id varChar(36) NOT NULL,
    followed_id varChar(36) NOT NULL,
    notifications_on boolean DEFAULT TRUE,
    CONSTRAINT PK_user_following PRIMARY KEY (follower_id, followed_id), --COMPOSITE KEY
    CONSTRAINT FK_follower_id FOREIGN KEY (follower_id) REFERENCES users(user_id),
    CONSTRAINT FK_followed_id FOREIGN KEY (followed_id) REFERENCES users(user_id)
);

CREATE TABLE user_likes (
    event_id int NOT NULL,
    user_id varChar(36) NOT NULL,
    CONSTRAINT PK_user_event_likes PRIMARY KEY (event_id, user_id),
    CONSTRAINT FK_event_id FOREIGN KEY (event_id) REFERENCES events(event_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE friendship (
    requester_id varChar(36) NOT NULL,
    addressee_id varChar(36) NOT NULL,
    created_on timestampz DEFAULT now(),
    CONSTRAINT PK_requester_addressee_id PRIMARY KEY (requester_id, addressee_id),
    CONSTRAINT FK_requester_id FOREIGN KEY (requester_id) REFERENCES users(user_id),
    CONSTRAINT FK_addressee_id FOREIGN KEY (addressee_id) REFERENCES users(user_id),
    CONSTRAINT CHK_friends_are_distinct CHECK (requester_id != addressee_id)  
);

CREATE TABLE status_code (
    code_id varChar(1) NOT NULL,
    status_name varChar(20) NOT NULL,
    CONSTRAINT PK_status_code PRIMARY KEY (code_id) 
);

CREATE TABLE friendship_status (
    requester_id varChar(36) NOT NULL,
    addressee_id varChar(36) NOT NULL,
    specified_on timestampz DEFAULT now(),
    status_code varChar(1) NOT NULL,
    specifier_id varChar(36) NOT NULL,
    CONSTRAINT PK_friendship_status PRIMARY KEY (requester_id, addressee_id, specified_on),
    CONSTRAINT FK_requester_id_addressee_id FOREIGN KEY (requester_id, addressee_id) REFERENCES friendship(requester_id, addressee_id),
    CONSTRAINT FK_specifier_id FOREIGN KEY (specifier_id) REFERENCES users(user_id),
    CONSTRAINT FK_status_code FOREIGN KEY (status_code) REFERENCES status_code(code_id),
    CONSTRAINT CHK_friends_are_different CHECK (requester_id != addressee_id)
);

CREATE TABLE chats (
    chat_id SERIAL NOT NULL,
    name varChar(20),
    created_on timestampz DEFAULT now(),
    CONSTRAINT PK_chat PRIMARY KEY (chat_id)
);

CREATE TABLE messages (
    message_id SERIAL NOT NULL,
    chat_id int NOT NULL,
    from_id varChar(36) NOT NULL,
    message varChar(200) NOT NULL,
    sent_on timestampz DEFAULT now(),
    CONSTRAINT PK_message PRIMARY KEY (message_id),
    CONSTRAINT FK_chat_id FOREIGN KEY (chat_id) REFERENCES chats(chat_id),
    CONSTRAINT FK_from_id FOREIGN KEY (from_id) REFERENCES users(user_id)
);

CREATE TABLE chat_users (
    user_id varChar(36) NOT NULL,
    chat_id int NOT NULL,
    joined_on timestampz DEFAULT now(),
    CONSTRAINT PK_chat_users PRIMARY KEY (chat_id, user_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_chat_id FOREIGN KEY (chat_id) REFERENCES chats(chat_id)
);

