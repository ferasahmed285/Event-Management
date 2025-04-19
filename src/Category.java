import org.w3c.dom.events.Event;//temp

import java.util.ArrayList;
import java.util.List;

public class Category {
    String categoryid;
    String categoryname;
    String description;


    public Category(String categoryid, String categoryname, String description) {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
        this.description = description;
    }
    public List<Event> getEvents(List<Event> allevents) {
        List<Event> filtered = new ArrayList<>();

        for (Event e : allevents) {
//            if (e.categoryid.equals(this.categoryid)) {
//                filtered.add(e);
//            }
        }

        return filtered;
    }
    public void updateDetails(String newName, String newDescription) {
        this.categoryname = newName;
        this.description = newDescription;
    }

}