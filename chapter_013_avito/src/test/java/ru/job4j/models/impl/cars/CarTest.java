package ru.job4j.models.impl.cars;

import org.junit.*;
import ru.job4j.dao.impl.ImplDao;
import ru.job4j.models.impl.users.Users;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class CarTest {
    private static ImplDao<Car> dao = ImplDao.getInstance();
    private static Car car;

    @BeforeClass
    public static void before() {
        car = new Car();
        car.setName("car");
        final Brand brand = new Brand();
        brand.setName("brand");
        final Carcase carcase = new Carcase();
        carcase.setName("carcase");
        final Category category = new Category();
        category.setName("category");
        final Users users = new Users();
        users.setLogin("login");
        users.setPassword("password");
        car.setBrand(brand);
        car.setCarcase(carcase);
        car.setCategory(category);
        car.setSaleStatus(SaleStatus.FORSALE);
        final Set<Users> authors = car.getAuthors();
        authors.add(users);
        car.setAuthors(authors);
        dao.add(car);
    }

    @AfterClass
    public static void  after() {
        dao.remove(car, Car.class);
    }

    @Test
    public void whenAddedCarThenContainsInDB() {
        final List<Car> all = dao.getAll(Car.class);
        assertThat(all.contains(car), is(true));
    }

    @Test
    public void whenUpdateCarThenReturnChangedCar() {
        car.setName("new name");
        final Car update = dao.update(car, Car.class);
        assertThat(update, is(dao.findByID(car.getId(), Car.class)));
    }
}