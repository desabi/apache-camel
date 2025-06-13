package com.desabi.guide.camel.soap.service;

import com.desabi.guide.camel.soap.model.Pet;
import com.desabi.guide.camel.soap.model.cfx.GetPetsResponse;
import com.desabi.guide.camel.soap.model.cfx.NoSuchPetException;

public interface PetService {

	void addPet(Pet pet);

	Pet getPet(Long id) throws NoSuchPetException;

	GetPetsResponse getPets();

	void updatePet(Long id, Pet pet) throws NoSuchPetException;

	void removePet(Long id) throws NoSuchPetException;
}