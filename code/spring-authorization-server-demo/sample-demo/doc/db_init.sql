CREATE TABLE customized_server.oauth2_registered_client (
    id                             varchar(100)  NOT NULL,
    client_id                      varchar(100)  NOT NULL,
    client_id_issued_at            timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    client_secret                  varchar(200)  DEFAULT NULL,
    client_secret_expires_at       timestamp     DEFAULT NULL,
    client_name                    varchar(200)  NOT NULL,
    client_authentication_methods  varchar(1000) NOT NULL,
    authorization_grant_types      varchar(1000) NOT NULL,
    redirect_uris                  varchar(1000) DEFAULT NULL,
    post_logout_redirect_uris      varchar(1000) DEFAULT NULL,
    scopes                         varchar(1000) NOT NULL,
    client_settings                varchar(2000) NOT NULL,
    token_settings                 varchar(2000) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE customized_server.oauth2_authorization_consent (
    registered_client_id           varchar(100)  NOT NULL,
    principal_name                 varchar(200)  NOT NULL,
    authorities                    varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*
IMPORTANT:
    If using PostgreSQL, update ALL columns defined with 'blob' to 'text',
    as PostgreSQL does not support the 'blob' data type.
*/
CREATE TABLE customized_server.oauth2_authorization (
    id                             varchar(100)  NOT NULL,
    registered_client_id           varchar(100)  NOT NULL,
    principal_name                 varchar(200)  NOT NULL,
    authorization_grant_type       varchar(100)  NOT NULL,
    authorized_scopes              varchar(1000) DEFAULT NULL,
    attributes                     blob          DEFAULT NULL,
    state                          varchar(500)  DEFAULT NULL,
    authorization_code_value       blob          DEFAULT NULL,
    authorization_code_issued_at   timestamp     DEFAULT NULL,
    authorization_code_expires_at  timestamp     DEFAULT NULL,
    authorization_code_metadata    blob          DEFAULT NULL,
    access_token_value             blob          DEFAULT NULL,
    access_token_issued_at         timestamp     DEFAULT NULL,
    access_token_expires_at        timestamp     DEFAULT NULL,
    access_token_metadata          blob          DEFAULT NULL,
    access_token_type              varchar(100)  DEFAULT NULL,
    access_token_scopes            varchar(1000) DEFAULT NULL,
    oidc_id_token_value            blob          DEFAULT NULL,
    oidc_id_token_issued_at        timestamp     DEFAULT NULL,
    oidc_id_token_expires_at       timestamp     DEFAULT NULL,
    oidc_id_token_metadata         blob          DEFAULT NULL,
    refresh_token_value            blob          DEFAULT NULL,
    refresh_token_issued_at        timestamp     DEFAULT NULL,
    refresh_token_expires_at       timestamp     DEFAULT NULL,
    refresh_token_metadata         blob          DEFAULT NULL,
    user_code_value                blob          DEFAULT NULL,
    user_code_issued_at            timestamp     DEFAULT NULL,
    user_code_expires_at           timestamp     DEFAULT NULL,
    user_code_metadata             blob          DEFAULT NULL,
    device_code_value              blob          DEFAULT NULL,
    device_code_issued_at          timestamp     DEFAULT NULL,
    device_code_expires_at         timestamp     DEFAULT NULL,
    device_code_metadata           blob          DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


示例数据:

1929d70f-a810-4082-a7dc-ad4d089c8989	messaging-client	2024-12-05 10:54:29.661501	{noop}secret	null	1929d70f-a810-4082-a7dc-ad4d089c8989	client_secret_basic	refresh_token,client_credentials,authorization_code	http://127.0.0.1:8080/authorized,http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc	http://127.0.0.1:8080/logged-out	user.read,openid,profile,message.read,message.write	{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}	{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}
67aab7af-8241-4c2a-bcf1-a313ef0dd295	device-messaging-client	2024-12-05 10:54:29.745891	null	null	67aab7af-8241-4c2a-bcf1-a313ef0dd295	none	refresh_token,urn:ietf:params:oauth:grant-type:device_code			message.read,message.write	{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}	{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}
