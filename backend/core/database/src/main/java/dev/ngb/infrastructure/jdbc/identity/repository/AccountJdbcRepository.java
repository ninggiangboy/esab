package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.auth.Account;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountJdbcEntity;
import dev.ngb.infrastructure.jdbc.identity.mapper.AccountJdbcMapper;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountJdbcRepository extends JdbcRepository<Account, AccountJdbcEntity, Long> implements AccountRepository {

    public AccountJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate
    ) {
        super(AccountJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, AccountJdbcMapper.INSTANCE);
    }

    @Override
    public boolean existsByEmail(String email) {
        return existsByFieldEqual("email", email);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return findFirstByFieldEqual("email", email);
    }
}
