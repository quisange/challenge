package org.develcorp.services.customer.services;

import org.develcorp.services.customer.entities.Person;
import org.develcorp.services.customer.repository.PersonRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class PersonServiceImpl <E extends Person, ID extends Serializable> implements PersonService <E, ID>{

    protected PersonRepository<E, ID> personRepository;

    public PersonServiceImpl(PersonRepository<E, ID> personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public List<E> listAll() {
        return personRepository.findAll();
    }

    @Override
    public E get(ID id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public E save(E entity) {
        return personRepository.save(entity);
    }

    @Override
    public E update(E entity) {
        Optional<E> entityOptional = personRepository.findById((ID) entity.getPersonId());
        E entityUpdate = entityOptional.get();
        return personRepository.save(entity);
    }

    @Override
    public E delete(ID id) {
        if (personRepository.existsById(id)) {
            return personRepository.save(get(id));
        }
        return null;
    }
}
