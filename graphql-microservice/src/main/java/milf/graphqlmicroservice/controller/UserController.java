package milf.graphqlmicroservice.controller;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import milf.graphqlmicroservice.domain.user.User;
import milf.graphqlmicroservice.service.GqlPostService;
import milf.graphqlmicroservice.service.GqlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.support.DataFetchingEnvironmentMethodArgumentResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private GqlUserService gqlUserService;


    @QueryMapping
    public Mono<User> getMyProfile(GraphQLContext context) {
        String id = context.getOrDefault("X-auth-user-id", "default");
        return gqlUserService.getUserById(id);
    }

    @QueryMapping
    public Mono<User> getUserById(@Argument String id) {
        return gqlUserService.getUserById(id);
    }
}
