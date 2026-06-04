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

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("john123");
        user.setPassword("qwerty123");
        user.setAge(20);

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
        User user = new User();
        user.setLogin(null);
        user.setPassword("qwerty123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("johnd");
        user.setPassword("qwerty123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthSix_ok() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("qwerty123");
        user.setAge(20);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("john123");
        user.setPassword("pop");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("john123");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthSix_ok() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("qwerty");
        user.setAge(20);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordFiveChars_notOk() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("pndrt");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordEightChars_ok() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("pndrt123");
        user.setAge(20);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("qwerty");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underage_notOk() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("qwerty");
        user.setAge(15);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("john12");
        user.setPassword("qwerty");
        user.setAge(-5);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageEighteen_ok() {
        User user = new User();
        user.setLogin("john123");
        user.setPassword("qwerty");
        user.setAge(18);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_existingLogin_notOk() {
        User existUser = new User();
        existUser.setLogin("john123");
        existUser.setPassword("qwerty");
        existUser.setAge(20);
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
