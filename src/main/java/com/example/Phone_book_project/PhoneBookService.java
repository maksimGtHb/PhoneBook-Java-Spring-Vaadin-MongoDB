package com.example.Phone_book_project;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneBookService {

    private final PhoneBookRepository phoneBookRepository;

    public PhoneBookService(PhoneBookRepository phoneBookRepository) {
        this.phoneBookRepository = phoneBookRepository;

    }

    public List<Contacts> getAllContacts(){
         return phoneBookRepository.findAll();
    }

    public Contacts createContact (Contacts contact){
        return phoneBookRepository.save(contact);
    }

}
