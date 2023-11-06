package ru.inno.testData;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.inno.model.CompanyEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class CompanyTestData {

    @Value("${names.prefix}")
    private String prefix;

     Faker faker = new Faker();

    public CompanyEntity getCompanyDBData() {
        int companyId = faker.number().numberBetween(1000, 10000);
        String companyName = prefix + faker.twinPeaks().character();
        String companyDescription = prefix + faker.twinPeaks().quote();
        boolean isActive = true;
        Timestamp createDateTime = Timestamp.valueOf(LocalDateTime.now());
        Timestamp changedTimestamp = Timestamp.valueOf(LocalDateTime.now());
        return new CompanyEntity(companyId, isActive, createDateTime, changedTimestamp, companyName, companyDescription);
    }
}