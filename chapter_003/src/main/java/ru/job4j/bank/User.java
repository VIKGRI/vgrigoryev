package ru.job4j.bank;

/**
 * Class represent user.
 *
 * @author vgrigoryev
 * @version 1
 * @since 19.09.2017
 */
public class User {
    /**
     * User's name.
     */
    private String name;
    /**
     * User's passport.
     */
    private int passport;

    /**
     * Constructor with parameters.
     *
     * @param name     User's name
     * @param passport User's passport
     * @throws UnavailableUserDataException if the user's data is not correct
     */
    public User(String name, int passport) throws UnavailableUserDataException {
        if (name != null && passport > 0) {
            this.name = name;
            this.passport = passport;
        } else {
            throw new UnavailableUserDataException("Неверные данные пользователя");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (passport != user.passport) {
            return false;
        }
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + passport;
        return result;
    }

    /**
     * Passport's getter.
     *
     * @return User's passport
     */
    public int getPassport() {
        return passport;
    }

    /**
     * Name's getter.
     *
     * @return User's name
     */
    public String getName() {
        return name;
    }
}