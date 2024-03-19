package pg.proj.pg.password.provider;

import lombok.AllArgsConstructor;
import pg.proj.pg.password.info.PasswordInfo;

@AllArgsConstructor
public class PasswordProviderImpl implements PasswordProvider {

    private final PasswordInfo template;

    @Override
    public PasswordInfo getPasswordInfo() {
        return new PasswordInfo(template.content());
    }
}
