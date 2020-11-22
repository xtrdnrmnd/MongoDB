//xtrdnrmnd 2020

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
                    System.out.println("Укажите id пользователя, данные которого вы хотите удалить:");
                    int id_delete = in.nextInt();
                    DBObject query2 = new BasicDBObject("_id", id_delete);
                    DBCursor cursor2 = students.find(query2);
                    if (cursor2.one() == null)
                        System.out.println("Пользователя с таким айди не существует");
                    else {
                        students.findAndRemove(query2);
                        System.out.println("Пользователь удален!");
                        break;
                    }
                case 3: // View all the documents in the collection
                    DBObject query3 = new BasicDBObject();
                    DBCursor cursor3 = students.find(query3);
                    System.out.println("Все записи в коллекции:");
                    while (cursor3.hasNext()) {
                        DBObject next = cursor3.next();
                        System.out.println(next);
                    }
                    break;
                case 4: // Modify the document in the collection
                    System.out.println("Укажите id пользователя, данные которого вы хотите изменить:");
                    int id_change = in.nextInt();
                    DBObject query4 = new BasicDBObject("_id", id_change);
                    DBCursor cursor4 = students.find(query4);
                    if (cursor4.one() == null)
                        System.out.println("Пользователя с таким айди не существует");
                    else {
                        Objects modifier = new Objects();
                        modifier.setStd_id(id_change);
                        System.out.println("Введите новую фамилию:");
                        String new_name = in.next();
                        modifier.setStd_fullname(new_name);
                        System.out.println("Введите новый логин: ");
                        String new_login = in.next();
                        modifier.setStd_login(new_login);
                        System.out.println("Введите новый пароль: ");
                        String new_pswd = in.next();
                        modifier.setStd_pswd(new_pswd);
                        students.findAndModify(query4, convert(modifier));
                        DBCursor cursor5 = students.find(query4);
                        System.out.println(cursor5.one());
                    }
            }
        } while (num!=5);
    }
    public static DBObject convert(Objects object) {
        return new BasicDBObject("_id", object.getStd_id()).append("std_fullname", object.getStd_fullname()).append("std_login", object.getStd_login()).append("std_pswd", object.getStd_pswd());
    }
}
