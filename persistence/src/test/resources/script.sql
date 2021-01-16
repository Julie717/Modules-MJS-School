CREATE TABLE tag (
  id_tag INT NOT NULL AUTO_INCREMENT,
  name_tag VARCHAR(45) NOT NULL,
  PRIMARY KEY (id_tag),
  UNIQUE INDEX name_UNIQUE (name_tag ASC));

CREATE TABLE gift_certificate (
  id_gift_certificate INT NOT NULL AUTO_INCREMENT,
  name_gift_certificate VARCHAR(45) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  price DECIMAL NOT NULL,
  duration INT NOT NULL,
  create_date TIMESTAMP NOT NULL,
  last_update_date TIMESTAMP NOT NULL,
  PRIMARY KEY (id_gift_certificate),
  UNIQUE INDEX name_gift_UNIQUE (name_gift_certificate ASC));

CREATE TABLE gift_certificate_tag (
  id_gift_certificate INT NOT NULL,
  id_tag INT NOT NULL,
  PRIMARY KEY (id_gift_certificate, id_tag),
  FOREIGN KEY (id_gift_certificate) REFERENCES gift_certificate (id_gift_certificate) ON DELETE CASCADE,
  FOREIGN KEY (id_tag) REFERENCES tag (id_tag) ON DELETE CASCADE);