import com.mongodb.*;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MongoDB {
    public static MongoClient mongoClient;
    public static DB database; //База данных
    public static DBCollection students; //Коллекция
    public static void main(String[] args) throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("stud");
        students = database.getCollection("students");
        int num;
        do {
            System.out.println("Выберите действие для коллекции:\n1.Добавить учетную запись" +
                    "\n2.Удалить учетную запись\n3.Посмотреть учетные записи\n4.Изменить учетную запись\n");
            Scanner in = new Scanner(System.in);
            num = in.nextInt();
            switch (num) {
                case 1: // Add a document to the collection
                    Objects object = new Objects();
                    System.out.println("Введите айди студента: ");
                    int id = in.nextInt();
                    object.setStd_id(id);
                    System.out.println("Введите имя студента: ");
                    String fio = in.next();
                    object.setStd_fullname(fio);
                    System.out.println("Введите логин студента: ");
                    String login = in.next();
                    object.setStd_login(login);
                    System.out.println("Введите пароль студента: ");
                    String pass = in.next();
                    object.setStd_pswd(pass);
                    students.insert(convert(object));
                    DBObject query = new BasicDBObject("_id", object.getStd_id());
                    DBCursor cursor = students.find(query);
                    System.out.println("Пользователь "+cursor.one()+" добавлен в коллекцию!");
                    break;
                case 2: // Delete a document from the collection
                case 3: // View all the documents in the collection
                    DBObject query2 = new BasicDBObject();
                    DBCursor cursor2 = students.find(query2);
                    System.out.println("Все записи в коллекции:");
                    while (cursor2.hasNext()) {
                        DBObject next = cursor2.next();
                        System.out.println(next);
                    }
                case 4: // Modify the document in the collection
            }
        } while (num!=5);
    }
    public static DBObject convert(Objects object) {
        return new BasicDBObject("_id", object.getStd_id()).append("std_fullname", object.getStd_fullname()).append("std_login", object.getStd_login()).append("std_pswd", object.getStd_pswd());
    }
}
