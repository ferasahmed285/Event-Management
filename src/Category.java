import java.util.ArrayList;
import java.util.List;
public class Category{
    String categoryid;
    String categoryname;
    String description;

    public Category(String categoryid, String categoryname, String description) {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
        this.description = description;
        Database.addEntity(this);
    }

    public String getCategoryid(){
        return categoryid;
    }

    public String getCategoryname(){
        return categoryname;

    }

    public String getDescription(){
        return description;

    }

    public List<Event> getEvents(List<Event> allevents) {
        List<Event> filtered = new ArrayList<>();

        for (Event e : allevents) {
            if (e.getCategory() != null && e.getCategory().equalsIgnoreCase(this.categoryname)) {
                filtered.add(e);
            }
        }

        return filtered;
    }

    public void updateDetails(String newname, String newdescription) {
        this.categoryname = newname;
        this.description = newdescription;
    }

}
