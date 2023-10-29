package ru.inno;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.inno.api.CompanyService;
import ru.inno.db.CompanyRepository;
import ru.inno.model.Company;
import ru.inno.model.CompanyEntity;

import java.sql.SQLException;
import java.util.List;

import static io.qameta.allure.Allure.step;

@SpringBootTest
class XClientsFinalExamApplicationTests {

    @Autowired
    private CompanyRepository companyRepoService;
    //    @Autowired
//    private EmployeeRepository employeeRepoService;
    @Autowired
    private CompanyService companyApiService;
//    @Autowired
//    private EmployeeService employeeAPIService;

    XClientsFinalExamApplicationTests() {
    }


    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Проверить, что список компаний фильтруется по параметру active")
    public void shouldFilterCompaniesByIsActive() {
        step("Получить список всех компаний по API");
        List<Company> companiesByApi = companyApiService.getAll();
        step("Получить список всех компаний из DB");
        List<CompanyEntity> companiesBySqlQuery = companyRepoService.getAll();

        step("Сверить длинны списков", () -> {
            Assertions.assertEquals(companiesByApi.size(), companiesBySqlQuery.size());
        });
        step("Получить список всех активных компаний по API");
        List<Company> activeCompaniesByApi = companyApiService.getAll(true);
        step("Получить список всех активных компаний из DB");
        List<CompanyEntity> activeCompaniesBySqlQuery = companyRepoService.getAll(true);
        step("Проверить длинны списков");
        Assertions.assertEquals(activeCompaniesByApi.size(), activeCompaniesBySqlQuery.size());

    }

    @Test
    @DisplayName("Проверить создание сотрудника в несуществующей компании")
    public void shouldNotCreateEmployeeToAbsentCompany() {


//      Генерируем несуществующий companyId

//      Запрашиваем размер списка employee до попытки создания employee с несуществующим companyId

//      Генерируем тестовые данные для employee

//      Проверяем, что при попытке создания employee через апи появляется ошибка
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
