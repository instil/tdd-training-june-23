package demos.cucumber.steps.list;


import demos.cucumber.list.ItemNotInListException;
import demos.cucumber.list.LinkedList;
import demos.cucumber.list.ListEmptyException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListStepDefinitions {
    private LinkedList list;
    
    @Given("a new list")
    public void newList() {
        list = new LinkedList();
    }
    @Given("an empty list")
    public void emptyList() {
        list = new LinkedList();
    }
    @Given("^a list of size (\\d+)$")
    public void listOfSize(Integer size){
        list = new LinkedList();
        for (int i = 0; i < size; i++) {
            list.add("foo" + i);
        }
    }
    @Given("^a list holding (.*)$")
    public void listHolding(String items){
        list = new LinkedList();
        for (String item : items.split("\\W+")) {
            list.add(item);
        }
    }
    @When("^we add in (\\w+)$")
    public void addIn(String item) {
        list.add(item);
    }
    @When("^we remove (\\w+)$")
    public void remove(String item) {
        removeItem(item);
    }
    @When("^we add all of (.*)$")
    public void addAllOf(String items){
        for (String item : items.split("\\W+")) {
            list.add(item);
        }
    }
    @Then("^the content is (.*)$")
    public void theContentIs(String expected){
        String[] items = expected.split("\\W+");
        assertEquals(items.length, list.size());
        for (int i = 0; i < items.length; i++) {
            assertEquals(items[i], accessItem(i));
        }
    }
    @Then("^the size is (\\d+)$")
    public void theSizeIs(Integer size) {
        assertEquals(size.intValue(), list.size());
    }
    @Then("the adjusted content is:")
    public void adjustedContentIs(DataTable input){
        Map<Integer, String> expectedItems = input.asMap(Integer.class, String.class);
        assertEquals(expectedItems.size(), list.size());
        for (Map.Entry<Integer, String> row : expectedItems.entrySet()) {
            assertEquals(row.getValue(), accessItem(row.getKey()));
        }
    }
    @Then("the list is empty")
    public void listIsEmpty(){
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    public void removeItem(String item) {
        try {
            list.remove(item);
        } catch (ListEmptyException | ItemNotInListException e) {
            throw new RuntimeException(e);
        }
    }

    public Object accessItem(int index) {
        try {
            return list.get(index);
        } catch (ListEmptyException | ItemNotInListException e) {
            throw new RuntimeException(e);
        }
    }
}
