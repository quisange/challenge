package org.develcorp.services.customer.services;

import org.develcorp.services.customer.entities.Person;

import java.io.Serializable;
import java.util.List;

public interface PersonService <E extends Person, ID extends Serializable> {

    List<E> listAll();

    E get(ID id);

    E save(E customer);

    E update(E customer);

    E delete(ID id);

}
