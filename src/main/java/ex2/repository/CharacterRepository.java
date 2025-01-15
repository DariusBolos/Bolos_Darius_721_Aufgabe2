package ex2.repository;

import ex2.model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a singleton repository for the Character model which performs the basic CRUD operations.
 */
public class CharacterRepository {
    public static CharacterRepository instance = null;
    private List<Character> characters;

    private CharacterRepository() {
        this.characters = new ArrayList<>();
        characters.add(new Character(1, "Darius", "Cluj-Napoca"));
    }

    public static CharacterRepository getInstance() {
        if (instance == null) {
            instance = new CharacterRepository();
        }

        return instance;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void addCharacter(Character character) {
        character.setId(characters.size() + 1);
        this.characters.add(character);
    }

    public void updateCharacter(Character character) {
        this.characters.set(characters.indexOf(character), character);
    }

    public void removeCharacter(int clientId) {
        List<Character> newCharacters = this.characters
                 .stream()
                 .filter(c -> c.getId() != clientId)
                .collect(Collectors.toCollection(ArrayList::new));

        this.setCharacters(newCharacters);
    }
}
