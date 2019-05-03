package model.model;

import java.util.HashMap;
import java.util.Map;

public class Contact {

    // contacts are kept in HashMap: key is type of contact ("Phone number", "Skype", etc.) and value is concrete value
    private Map<String, String> contacts = new HashMap<>();


    public Contact(String a, String b) {
    }
}
