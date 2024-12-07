CREATE TABLE Cinemas (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         address VARCHAR(255),
                         type VARCHAR(255)
);
CREATE TABLE HallLayout (
                            layId SERIAL PRIMARY KEY,
                            linkLay VARCHAR(255)
);
CREATE TABLE Halls (
                       id BIGSERIAL PRIMARY KEY,
                       cinema_id BIGINT REFERENCES Cinemas(id),
                       hallLayId BIGINT references HallLayout(layId),
                       quantityRow INTEGER,
                       quantityPlace INTEGER,
                       maketId		INTEGER
);
CREATE TABLE Producer (
                          producerId SERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          surName VARCHAR(255)
);
CREATE TABLE Genre (
                       genreId SERIAL PRIMARY KEY,
                       name VARCHAR(255)
);
CREATE TABLE Country (
                         countryId SERIAL PRIMARY KEY,
                         name VARCHAR(255),
                         code VARCHAR(255),
                         photo VARCHAR(255)
);

CREATE TABLE Film (
                      filmId BIGINT PRIMARY KEY,
                      producerFk BIGINT REFERENCES Producer(producerId),
                      countryFk BIGINT REFERENCES Genre(genreId),
                      genreFk BIGINT REFERENCES Country(countryId),
                      year INT,
                      duration INT,
                      title VARCHAR(255),
                      mark FLOAT
);
create table Session(
                        sessionId SERIAL PRIMARY KEY,
                        date VARCHAR(255),
                        type VARCHAR(255),
                        hall_id BIGINT REFERENCES Halls(id),
                        filmFk BIGINT references Film(filmId)
);

CREATE TABLE MovieUser (
                           userId BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255),
                           surName VARCHAR(255),
                           email VARCHAR(255),
                           telephoneNumber BIGINT,
                           password VARCHAR(255),
                           login VARCHAR(255)
);

CREATE TABLE Ticket (
                        ticketId SERIAL PRIMARY KEY,
                        row INTEGER,
                        place INTEGER,
                        price FLOAT,
                        ticketFk BIGINT REFERENCES MovieUser(userId),
                        sessionFk BIGINT references Session(sessionId)
);


CREATE TABLE Feedback (
                          feedbackId BIGSERIAL PRIMARY KEY,
                          filmId BIGINT REFERENCES Film(filmId),
                          movieUserFk BIGINT REFERENCES MovieUser(userId),
                          feedbackText TEXT
);
