-- liquibase formatted sql

-- changeset action:drop_index
ALTER TABLE book DROP CONSTRAINT book_isbn_key;
