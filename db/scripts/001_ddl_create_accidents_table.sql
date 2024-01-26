CREATE TABLE accident_type (
  id serial primary key,
  "name" text
);

CREATE TABLE accidents (
  id serial primary key,
  "name" text,
  "text" text,
  address text,
  type int REFERENCES accident_type(id) NOT NULL);


CREATE TABLE rules (
  id serial primary key,
  "name" text
);

create table accident_rules(
    id   serial primary key,
    accidents_id int REFERENCES accidents(id)    NOT NULL,
    rule_id int REFERENCES rules(id)    NOT NULL,
    unique (accidents_id, rule_id)
);