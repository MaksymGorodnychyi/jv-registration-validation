package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = createUser("john123", "qwerty123", 20);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createUser(null, "qwerty123", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createUser("johnd", "qwerty123", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthSix_ok() {
        User user = createUser("john12", "qwerty123", 20);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createUser("john123", "pop", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createUser("john123", null, 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthSix_ok() {
        User user = createUser("john12", "qwerty", 20);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = createUser("john12", "", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordFiveChars_notOk() {
        User user = createUser("john12", "pndrt", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordEightChars_ok() {
        User user = createUser("john12", "pndrt123", 20);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullAge_notOk() {
        User user = createUser("john12", "qwerty", null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underage_notOk() {
        User user = createUser("john12", "qwerty", 15);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createUser("john12", "qwerty", -5);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageEighteen_ok() {
        User user = createUser("john123", "qwerty", 18);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_existingLogin_notOk() {
        User existUser = createUser("john123", "qwerty", 20);
        Storage.people.add(existUser);

        User newUser = new User();
        newUser.setLogin("john123");
        newUser.setPassword("qwerty1456");
        newUser.setAge(25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals(1, Storage.people.size());
    }
}
