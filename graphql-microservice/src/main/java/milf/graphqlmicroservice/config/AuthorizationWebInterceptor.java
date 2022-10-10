package milf.graphqlmicroservice.config;

import org.apache.http.HttpRequestInterceptor;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationWebInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String userId = request.getHeaders().getFirst("X-auth-user-id");
        String userRoles = request.getHeaders().getFirst("X-auth-user-roles");
        request.configureExecutionInput((input, inputBuilder) ->
                inputBuilder
                        .graphQLContext(contextBuilder -> contextBuilder.put("X-auth-user-id", userId))
                        .graphQLContext(contextBuilder -> contextBuilder.put("X-auth-user-roles", userRoles))
                        .build()
        );
        return chain.next(request);
    }
}
