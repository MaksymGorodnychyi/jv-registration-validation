package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Not fit");
        }
        String login = user.getLogin();
        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not fit");
        }
        String password = user.getPassword();
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Not fit");
        }
        Integer age = user.getAge();
        if (age == null || age < MIN_AGE) {
            throw new RegistrationException("Not fit");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Not fit");
        }
        return storageDao.add(user);
    }
}
