package ru.inno.testData;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.inno.model.Employee;

@Component
public class EmployeeTestsData {

    @Value("${names.prefix}")
    protected String prefix;
    Faker faker = new Faker();


    public Employee getEmployeeAPIData(int companyId) {
        int id = faker.number().numberBetween(3000, 30000);
        String firstName = prefix + faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String url = faker.internet().url();
        String phone = String.valueOf(faker.number().digits(10));
        String birthDate = faker.date().birthday().toString();
        return new Employee(id, firstName, lastName, companyId, email, url, phone, birthDate, false);
    }
}