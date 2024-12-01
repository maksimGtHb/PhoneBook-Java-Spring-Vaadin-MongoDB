package com.example.Phone_book_project;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhoneBookRepository extends MongoRepository <Contacts, String> {
}
