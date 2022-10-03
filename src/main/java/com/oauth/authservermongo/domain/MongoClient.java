package com.oauth.authservermongo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Document("client_detail")
@Data
public class MongoClient {

    @Id
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private ClientSettings clientSettings;
    private Long accessTokenTimeToLive;
    private OAuth2TokenFormat accessTokenFormat;
    private Boolean reuseRefreshTokens;
    private Long refreshTokenTimeToLive;
    private SignatureAlgorithm idTokenSignatureAlgorithm;



    public static MongoClient convert(RegisteredClient registeredClient) {
        MongoClient mongoClient = new MongoClient();
        mongoClient.setId(registeredClient.getId());
        mongoClient.setClientId(registeredClient.getClientId());
        mongoClient.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        mongoClient.setClientSecret(registeredClient.getClientSecret());
        mongoClient.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
        mongoClient.setClientName(registeredClient.getClientName());
        mongoClient.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods());
        mongoClient.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes());
        mongoClient.setRedirectUris(registeredClient.getRedirectUris());
        mongoClient.setScopes(registeredClient.getScopes());
        mongoClient.setClientSettings(registeredClient.getClientSettings());
        mongoClient.setAccessTokenTimeToLive(registeredClient.getTokenSettings().getAccessTokenTimeToLive().toMillis());
        mongoClient.setAccessTokenFormat(registeredClient.getTokenSettings().getAccessTokenFormat());
        mongoClient.setReuseRefreshTokens(registeredClient.getTokenSettings().isReuseRefreshTokens());
        mongoClient.setRefreshTokenTimeToLive(registeredClient.getTokenSettings().getRefreshTokenTimeToLive().toMillis());
        mongoClient.setIdTokenSignatureAlgorithm(registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm());
        return mongoClient;

    }

    public static RegisteredClient convertToRegisterClient(MongoClient mongoClient) {
        Map<String,Object> settings = new LinkedHashMap<>();
        settings.put(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE,Duration.ofMillis(mongoClient.getAccessTokenTimeToLive()));
        settings.put(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT, mongoClient.getAccessTokenFormat());
        settings.put(ConfigurationSettingNames.Token.REUSE_REFRESH_TOKENS,mongoClient.getReuseRefreshTokens());
        settings.put(ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE, Duration.ofMillis(mongoClient.getRefreshTokenTimeToLive()));
        settings.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, mongoClient.getIdTokenSignatureAlgorithm());
        TokenSettings tokenSettings = TokenSettings.withSettings(settings).build();
        RegisteredClient registeredClient = RegisteredClient.withId(mongoClient.getId())
                .clientId(mongoClient.getClientId())
                .clientIdIssuedAt(mongoClient.getClientIdIssuedAt())
                .clientSecret(mongoClient.getClientSecret())
                .clientSecretExpiresAt(mongoClient.getClientSecretExpiresAt())
                .clientAuthenticationMethods((methods)->methods.addAll(mongoClient.getClientAuthenticationMethods()))
                .authorizationGrantTypes((types)->types.addAll(mongoClient.getAuthorizationGrantTypes()))
                .redirectUris((uris)->uris.addAll(mongoClient.getRedirectUris()))
                .scopes((scopes)-> scopes.addAll(mongoClient.getScopes()))
                .clientSettings(mongoClient.getClientSettings())
                .tokenSettings(tokenSettings)
                .build();
        return registeredClient;
    }
}
