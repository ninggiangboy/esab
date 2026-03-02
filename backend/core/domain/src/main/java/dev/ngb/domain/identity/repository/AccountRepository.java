package dev.ngb.domain.identity.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.identity.model.account.Account;

/**
 * Repository for managing {@link Account} aggregates.
 */
public interface AccountRepository extends Repository<Account, Long> {
}
