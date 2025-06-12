package com.desabisc.guide.camel.storage;

import com.desabisc.guide.camel.entity.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ExamplePetStorage {
    private ConcurrentHashMap<Long, Pet> pets = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public void createPet(Pet pet) {
        pet.setId(idCounter.getAndIncrement());
        pets.put(pet.getId(), pet);
    }

    public Pet getPet(long id) {
        return pets.get(id);
    }

    public void updatePet(Pet pet) {
        pets.put(pet.getId(), pet);
    }

    public void deletePet(long id) {
        pets.remove(id);
    }

    // when a method returns a value, this value is set as body in camel message
    public List<Pet> getAllPets() {
        return new ArrayList<>(pets.values());
    }
}
