package ru.inno;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.inno.api.AuthorizeService;
import ru.inno.api.CompanyService;
import ru.inno.api.EmployeeService;
import ru.inno.db.CompanyRepository;
import ru.inno.db.EmployeeRepository;
import ru.inno.model.Company;
import ru.inno.model.CompanyEntity;
import ru.inno.model.Employee;

import java.sql.SQLException;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.inno.testData.EmployeeTestsData.getRandomEmployee;

@SpringBootTest
class XClientsFinalExamApplicationTests {

    @Autowired
    private CompanyRepository companyRepoService;
    @Autowired
    private EmployeeRepository employeeRepoService;
    @Autowired
    private CompanyService companyApiService;
    @Autowired
    private EmployeeService employeeAPIService;
    @Autowired
    private AuthorizeService authAPIService;
    Faker faker = new Faker();

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Проверить, что список компаний фильтруется по параметру active")
    public void shouldFilterCompaniesByIsActive() {
        step("Получить список всех компаний по API");
        List<Company> companiesByApi = companyApiService.getAll();
        step("Получить список всех компаний из БД");
        List<CompanyEntity> companiesBySqlQuery = companyRepoService.getAll();
        step("Сверить длинны списков", () -> {
            assertEquals(companiesByApi.size(), companiesBySqlQuery.size());
        });
        step("Получить список всех активных компаний по API");
        List<Company> activeCompaniesByApi = companyApiService.getAll(true);
        step("Получить список всех активных компаний из БД");
        List<CompanyEntity> activeCompaniesBySqlQuery = companyRepoService.getAll(true);
        step("Сравнить размеры списков");
        assertEquals(activeCompaniesByApi.size(), activeCompaniesBySqlQuery.size());

    }

    @Test
    @DisplayName("Проверить создание сотрудника в несуществующей компании")
    public void shouldNotCreateEmployeeToAbsentCompany() {
        //Генерируем несуществующий companyId
        Integer nonExistentCompanyId = faker.number().numberBetween(2000, 5000);
        //Генерируем тестовые данные Employee
        Employee employee = getRandomEmployee(nonExistentCompanyId);
        //Запрашиваем размер списка employee до попытки создания employee с несуществующим companyId
        int startListSize = employeeRepoService.getAll().size();
        //проверяем, что при попытке создания через апи появляется ошибка
        assertThrows(AssertionError.class, () -> employeeAPIService.create(employee, authAPIService.getToken()));
        //Перезапрашиваем размер списка employee
        int endListSize = employeeRepoService.getAll().size();
        //Проверяем, что размеры первого и второго списков равны
        assertEquals(startListSize, endListSize);
    }


    @Test
    @DisplayName("Проверить, что неактивный сотрудник не отображается в списке")
    public void shouldNotGetNonActiveEmployee() throws SQLException {
        //Создаем Company в бд

        //Создаем Employee в бд

        //У Employee устанавливаем isActive = false и сохраняем
        //Проверяем, что неактивный сотрудник не отображается при запросе по ID через API
    }

    @Test
    @DisplayName("Проверить, что у удаленной компании проставляется в БД поле deletedAt")
    public void shouldFillDeletedAtToDeletedCompany() {
        //Создаем Company в бд

        //Получаем Company из DB

        //Проверяем, что у Company deleted_at is null

        //Удаляем компанию
    }
}
