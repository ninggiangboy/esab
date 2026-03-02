package dev.ngb.infrastructure.jdbc.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class SoftDeleteAuditableEntity<ID> extends AuditableEntity<ID>
        implements SoftDeletable {

    private boolean deleted;

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted != null && deleted;
    }
}
