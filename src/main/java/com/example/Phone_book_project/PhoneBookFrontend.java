package com.example.Phone_book_project;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("")
public class PhoneBookFrontend extends VerticalLayout {

    private final PhoneBookService service;
    private final Grid <Contacts> grid;


    @Autowired
    public PhoneBookFrontend(PhoneBookService service){
        this.service = service;
        this.grid = new Grid<>(Contacts.class);


        grid.setItems(service.getAllContacts());
        grid.setColumns("id", "name", "phone", "email", "dateAdded");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        TextField name = new TextField("Имя");
        TextField phone = new TextField("Телефон");
        TextField email = new TextField("Эл. почта");

        FormLayout formLayout = new FormLayout(name, phone, email);

        // Reading from table and populate form fields
        grid.asSingleSelect().addValueChangeListener(event -> {
            Contacts selectedContact = event.getValue();
            if (selectedContact != null) {
                name.setValue(selectedContact.getName());
                phone.setValue(selectedContact.getPhone());
                email.setValue(selectedContact.getEmail());
            } else {
                name.clear();
                phone.clear();
                email.clear();
            }
        });


        Button saveUpdatedButton = new Button("Изменить", e -> {
            // Get the selected contact
            Contacts selectedContact = grid.asSingleSelect().getValue();
            if (selectedContact != null) {
                selectedContact.setName(name.getValue());
                selectedContact.setPhone(phone.getValue());
                selectedContact.setEmail(email.getValue());
                service.createContact(selectedContact); // Save updated contact
                grid.setItems(service.getAllContacts()); // Refresh grid
            }
        });


        //event that adds a new contact
        Button addContactButton = new Button("Добавить новый", e -> {
        Contacts contact = new Contacts();
        contact.setName(name.getValue());
        contact.setPhone(phone.getValue());
        contact.setEmail(email.getValue());
        contact.setDateAdded(java.time.LocalDate.now().toString());
        service.createContact(contact);
            grid.setItems(service.getAllContacts());
        });

        add(grid, formLayout, addContactButton, saveUpdatedButton);

    }
}
