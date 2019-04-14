import model.Resume;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class MainCollections {
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_CHECK = "uuid_check";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_CHECK = new Resume(UUID_CHECK);


    public static void main(String[] args) {

        Collection<Resume> collection = new LinkedList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);
        System.out.println(collection.toString());
        Iterator<Resume> iterator = collection.iterator();


        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals("uuid_2"))
                iterator.remove();

        }
        System.out.println(collection.toString());
    }

}
