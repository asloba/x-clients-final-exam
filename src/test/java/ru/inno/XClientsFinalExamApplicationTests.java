package ru.inno;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.inno.api.AuthorizeService;
import ru.inno.api.CompanyService;
import ru.inno.api.EmployeeService;
import ru.inno.db.CompanyRepository;
import ru.inno.db.EmployeeRepository;
import ru.inno.model.Company;
import ru.inno.model.CompanyEntity;
import ru.inno.model.Employee;
import ru.inno.testData.EmployeeTestsData;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XClientsFinalExamApplicationTests {

    @Value("${names.prefix}")
    String prefix;
    Faker faker = new Faker();
    @Autowired
    private CompanyRepository companyRepositoryService;
    @Autowired
    private EmployeeRepository employeeRepositoryService;
    @Autowired
    private CompanyService companyApiService;
    @Autowired
    private EmployeeService employeeAPIService;
    @Autowired
    private AuthorizeService authAPIService;
    @Autowired
    private EmployeeTestsData employeeTestsData;

    @AfterEach
    public void clearData() {
        employeeRepositoryService.clean(prefix);
        companyRepositoryService.clean(prefix);
    }

    @Test
    @DisplayName("Проверить, что список компаний фильтруется по параметру active")
    public void shouldFilterCompaniesByIsActive() {
        List<Company> companiesByApi =
                step("Получаем список всех компаний по API", () ->
                        companyApiService.getAll());
        List<CompanyEntity> companiesBySqlQuery =
                step("Получаем список всех компаний из БД", () ->
                        companyRepositoryService.getAll());
        step("Сверяем длины списков", () ->
                assertEquals(companiesByApi.size(), companiesBySqlQuery.size()));
        List<Company> activeCompaniesByApi =
                step("Получаем список всех активных компаний по API", () ->
                        companyApiService.getAll(true));
        List<CompanyEntity> activeCompaniesBySqlQuery =
                step("Получаем список всех активных компаний из БД", () ->
                        companyRepositoryService.getAll(true));
        step("Сравниваем размеры списков", () ->
                assertEquals(activeCompaniesByApi.size(), activeCompaniesBySqlQuery.size()));
    }

    @Test
    @DisplayName("Проверить создание сотрудника в несуществующей компании")
    public void shouldNotCreateEmployeeToAbsentCompany() {
        int nonExistentCompanyId =
                step("Генерируем несуществующий companyId", () ->
                        faker.number().numberBetween(2000, 5000));
        Employee employee =
                step("Генерируем тестовые данные Employee", () ->
                        employeeTestsData.getEmployeeAPIData(nonExistentCompanyId));
        int startListSize =
                step("Запрашиваем размер списка employee до попытки создания employee с несуществующим companyId", () ->
                        employeeRepositoryService.getAll().size());
        step("Проверяем, что при попытке создания через API появляется ошибка", () ->
                assertThrows(AssertionError.class, () -> employeeAPIService.create(employee, authAPIService.getToken())));
        int endListSize =
                step("Перезапрашиваем размер списка employee", () ->
                        employeeRepositoryService.getAll().size());
        step("Проверяем, что размеры первого и второго списков равны", () ->
                assertEquals(startListSize, endListSize));
    }


    /* По тексту кейса не очень понятно, в каком именно списке не должен отображаться неактивный сотрудник,
     поэтому сделала проверку на то,
     что неактивный сотрудник не должен отображаться в списке сотрудников конкретной компании
     Тест падает, т.к. на самом деле сотрудник отображается */
    @Test
    @DisplayName("Проверить, что неактивный сотрудник не отображается в списке")
    public void shouldNotGetNonActiveEmployee() {
        int companyId =
                step("Создаём компанию", () ->
                        companyRepositoryService.create());
        Employee employee =
                step("Генерируем тестовые данные Employee", () ->
                        employeeTestsData.getEmployeeAPIData(companyId));
        step("Создаём Employee в созданной компании", () ->
                employeeAPIService.create(employee, authAPIService.getToken()));
        step("Проверяем, что созданный неактивный Employee нее отображается в списке сотрудников компании", () -> {
            assertEquals(0, employeeAPIService.getAll(companyId).size());
            assertEquals(0, employeeRepositoryService.getAllByCompanyId(companyId).size());
        });
    }

    @Test
    @DisplayName("Проверить, что у удаленной компании проставляется в БД поле deletedAt")
    public void shouldFillDeletedAtToDeletedCompany() {
        int companyId =
                step("Создать компанию", () -> companyRepositoryService.create());
        step("Проверяем, что поле deleted_at у созданной компании равно null", () -> {
            CompanyEntity companyCreated = companyRepositoryService.getById(companyId);
            assertNull(companyCreated.getDeletedAt());
        });
        step("Удаляем компанию", () ->
                companyApiService.deleteById(companyId, authAPIService.getToken()));
        step("Дожидаемся обновления данных", () ->
                Thread.sleep(3000));
        step("Проверяем, что у Company в поле deleted_at значение не равно null", () -> {
            CompanyEntity companyDeleted = companyRepositoryService.getById(companyId);
            assertNotNull(companyDeleted.getDeletedAt());
        });
    }
}
