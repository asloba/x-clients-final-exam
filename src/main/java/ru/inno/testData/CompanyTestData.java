package ru.inno.testData;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import ru.inno.model.Company;

public class CompanyTestData {

    @Value("${names.prefix}")
    protected String prefix;

    Faker faker = new Faker();

    public Company getRandomCompany() {

        int companyId = faker.number().numberBetween(1000, 10000);
        String companyName = prefix + faker.twinPeaks().character();
        String companyDescription = prefix + faker.twinPeaks().quote();

        return new Company(companyId, companyName, companyDescription);
    }

}
