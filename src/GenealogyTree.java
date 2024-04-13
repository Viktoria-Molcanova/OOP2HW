import java.io.Serializable;
import java.util.List;

public class GenealogyTree implements Serializable {
    private final Person root;

    public GenealogyTree(Person root) {
        this.root = root;
    }

    public List<Person> getChildrenOfPerson(String personName) throws PersonNotFoundException {
        Person person = findPersonByName(root, personName);
        if (person == null) {
            throw new PersonNotFoundException("Член семьи не найден: " + personName);
        }
        return person.getChildren();
    }

    public void removePerson(String personName) throws PersonNotFoundException {
        Person person = findPersonByName(root, personName);
        if (person == null) {
            throw new PersonNotFoundException("Член семьи не найден: " + personName);
        }
        Person father = person.getFather();
        Person mother = person.getMother();
        if (father != null) {
            father.getChildren().remove(person);
        }
        if (mother != null) {
            mother.getChildren().remove(person);
        }
    }

    private Person findPersonByName(Person currentPerson, String personName) {
        if (currentPerson.getName().equals(personName)) {
            return currentPerson;
        }

        for (Person child : currentPerson.getChildren()) {
            Person foundPerson = findPersonByName(child, personName);
            if (foundPerson != null) {
                return foundPerson;
            }
        }

        return null;
    }
}