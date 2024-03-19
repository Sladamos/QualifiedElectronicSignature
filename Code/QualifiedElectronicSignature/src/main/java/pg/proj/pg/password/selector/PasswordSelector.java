package pg.proj.pg.password.selector;

import pg.proj.pg.password.provider.PasswordProvider;

public interface PasswordSelector {
    PasswordProvider selectPassword();
}
