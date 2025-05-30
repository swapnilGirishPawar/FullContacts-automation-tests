package utils;

import com.github.javafaker.Faker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CommonUtil {
    public static Properties Props;
    public static Faker faker = new Faker();

    public static String ReadProperties(String Property, String Location) {
        Props = new Properties();
        File FileLocation = new File(Location);
        try (FileReader ReadFile = new FileReader(FileLocation)) {
            Props.load(ReadFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Props.getProperty(Property);
    }
    public static String configCredential(String Property){
        return ReadProperties(Property, "src/test/resources/config.properties");
    }
    public static String randomEmail() {
        return faker.internet().emailAddress();
    }
    public static String randomPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }
    public static String randomFullName(){
        return faker.name().fullName().replace("\'", "");
    }
    public static String randomFirstName() {
        return faker.name().firstName().replace("\'", "");
    }
    public static String randomLastName(){
        return faker.name().lastName().replace("\'", "");
    }
}
