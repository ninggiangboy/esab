package dev.ngb.infrastructure.jdbc.base;

public interface SoftDeletable {

    boolean isDeleted();

    void setDeleted(Boolean deleted);
}
