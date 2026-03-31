package dev.ngb.gateway;

import org.jspecify.annotations.Nullable;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    public static final String X_USER_ID = "X-User-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .filter(JwtAuthenticationToken.class::isInstance)
                .cast(JwtAuthenticationToken.class)
                .map(token -> {
                    Jwt jwt = (Jwt) token.getPrincipal();
                    return withTrustedUserId(exchange, jwt);
                })
                .defaultIfEmpty(withTrustedUserId(exchange, null))
                .flatMap(chain::filter);
    }

    /**
     * Drops any client-supplied {@value #X_USER_ID}, then sets it only from JWT subject when present.
     */
    private static ServerWebExchange withTrustedUserId(ServerWebExchange exchange, @Nullable Jwt jwt) {
        ServerHttpRequest.Builder request = exchange.getRequest().mutate()
                .headers(h -> h.remove(X_USER_ID));
        if (jwt != null && jwt.getSubject() != null && !jwt.getSubject().isBlank()) {
            request.header(X_USER_ID, jwt.getSubject());
        }
        return exchange.mutate().request(request.build()).build();
    }

    @Override
    public int getOrder() {
        // Run after the security filter has populated the Principal
        return Ordered.LOWEST_PRECEDENCE;
    }
}
