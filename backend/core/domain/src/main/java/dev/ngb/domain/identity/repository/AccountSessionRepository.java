package dev.ngb.domain.identity.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.identity.model.session.AccountSession;

/**
 * Repository for managing {@link AccountSession} entities.
 */
public interface AccountSessionRepository extends Repository<AccountSession, Long> {
}
