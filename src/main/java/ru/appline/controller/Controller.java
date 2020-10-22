package ru.appline.controller;

import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.test.util.TestPropertyValues.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	public static final PetModel petModel = PetModel.getInstance();
	private static final AtomicInteger newId = new AtomicInteger(1);
	
	@PostMapping(value = "/createPet", consumes = "application/json", produces = "application/text")
	public String createPet(@RequestBody Pet pet) {
		petModel.add(pet, newId.getAndIncrement());
		String response = "Новый питомец внесен в базу";
		return response;
	}
	
	@GetMapping(value = "/getAll", produces = "application/json")
	public Map<Integer, Pet> getAll() {
		return petModel.getAll();
	}
	
	@GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
	public Pet getPet(@RequestBody Map<String, Integer> id) {
		return petModel.getFromList(id.get("id"));
	}
	
	@DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/text")
	public String delPet(@RequestBody Map.Entry<String, Integer> id) {
		petModel.del(id.getValue());
		String response = "Питомец удален из базы";
		return response;
	}
	
	@PutMapping(value = "/putPet", consumes = "application/json", produces = "application/text")
	public String putPet(@RequestBody Map.Entry<String, Pet> pet) {
		petModel.add(pet.getValue(), Integer.parseInt(pet.getKey()));
		String response = "Данные изменены";
		return response;
	}
	
}
