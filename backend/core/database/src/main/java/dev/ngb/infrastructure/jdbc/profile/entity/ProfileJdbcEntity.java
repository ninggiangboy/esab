package dev.ngb.infrastructure.jdbc.profile.entity;

import dev.ngb.domain.profile.model.profile.ProfileVisibility;
import dev.ngb.infrastructure.jdbc.base.entity.JdbcEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table("prf_profiles")
public class ProfileJdbcEntity extends JdbcEntity<Long> {

    private Long accountId;
    private String username;
    private String displayName;
    private String bio;
    private String website;
    private String location;
    private String avatarUrl;
    private String bannerUrl;
    private ProfileVisibility visibility;
    private Boolean isVerified;
}
