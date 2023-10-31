package ru.inno.testData;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import ru.inno.model.Employee;

public class EmployeeTestsData {

    @Value("${names.prefix}")
    private static String prefix;
    static Faker faker = new Faker();


    public static Employee getRandomEmployee(int companyId) {
        int id = 0;
        String firstName = prefix + faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String url = faker.internet().url();
        String phone = String.valueOf(faker.number().digits(10));
        String birthDate = faker.date().birthday().toString();
        return new Employee(id, firstName, lastName, companyId, email, url, phone, birthDate, true);
    }
}
