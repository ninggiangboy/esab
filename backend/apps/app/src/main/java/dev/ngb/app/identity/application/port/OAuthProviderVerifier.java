package dev.ngb.app.identity.application.port;

import dev.ngb.domain.identity.model.account.AuthProvider;

public interface OAuthProviderVerifier {

    OAuthUserInfo verify(AuthProvider provider, String providerToken);

    record OAuthUserInfo(String email, String providerAccountId) {}
}
