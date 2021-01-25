package net.deanly.demo.domain.integrated_searcher.provider;

import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryService;
import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryServiceProvider;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import net.deanly.demo.infrastructure.jpa.UsersRepository;

import java.lang.reflect.Type;

public class AllQueryServiceProvider implements IQueryServiceProvider {

    private final UsersRepository usersRepository;

    public AllQueryServiceProvider(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public IQueryService<?> access(Type identifierType, QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                if (identifierType.equals(Long.class)) {
                    return new UserJPAQueryService(usersRepository);
                }
            case ES_USERS:
            case API_USERS:
        }
        return null;
    }
}
