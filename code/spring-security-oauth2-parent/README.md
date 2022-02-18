## 启动DEMO

### 一、依次分别启动**oauth2-server**和**oauth2-client**两个项目

调用 [http://127.0.0.1:8080/oauth2/authorization/messaging](http://127.0.0.1:8080/oauth2/authorization/messaging)

输入用户名`lalita`和密码`123456`后即可返回OAuth2.0 Client的信息：

```json
{
  "oAuth2AuthorizedClient":{
    "clientRegistration":{
      "registrationId":"messaging",
      "clientId":"messaging-client",
      "clientSecret":"secret",
      "clientAuthenticationMethod":{
        "value":"client_secret_basic"
      },
      "authorizationGrantType":{
        "value":"authorization_code"
      },
      "redirectUri":"http://127.0.0.1:8080/foo/bar",
      "scopes":[
        "message.read",
        "message.write"
      ],
      "providerDetails":{
        "authorizationUri":"http://localhost:9000/oauth2/authorize",
        "tokenUri":"http://localhost:9000/oauth2/token",
        "userInfoEndpoint":{
          "uri":"http://localhost:9000/userinfo",
          "authenticationMethod":{
            "value":"header"
          },
          "userNameAttributeName":"sub"
        },
        "jwkSetUri":"http://localhost:9000/oauth2/jwks",
        "issuerUri":"http://localhost:9000",
        "configurationMetadata":{
          "authorization_endpoint":"http://localhost:9000/oauth2/authorize",
          "token_endpoint":"http://localhost:9000/oauth2/token",
          "issuer":"http://localhost:9000",
          "jwks_uri":"http://localhost:9000/oauth2/jwks",
          "scopes_supported":[
            "openid"
          ],
          "response_types_supported":[
            "code"
          ],
          "grant_types_supported":[
            "authorization_code",
            "client_credentials",
            "refresh_token"
          ],
          "token_endpoint_auth_methods_supported":[
            "client_secret_basic",
            "client_secret_post",
            "client_secret_jwt",
            "private_key_jwt"
          ],
          "request_uri_parameter_supported":true,
          "subject_types_supported":[
            "public"
          ],
          "userinfo_endpoint":"http://localhost:9000/userinfo",
          "id_token_signing_alg_values_supported":[
            "RS256"
          ]
        }
      },
      "clientName":"http://localhost:9000",
      "redirectUriTemplate":"http://127.0.0.1:8080/foo/bar"
    },
    "principalName":"anonymousUser",
    "accessToken":{
      "tokenValue":"eyJraWQiOiJmYmI2YWQ5My1mODU2LTQ0OWMtYjQ0Mi03MTk3YjZmMjhkNTEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJsYWxpdGEiLCJhdWQiOiJtZXNzYWdpbmctY2xpZW50IiwibmJmIjoxNjQ1MTY5NTU2LCJzY29wZSI6WyJtZXNzYWdlLndyaXRlIl0sImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo5MDAwIiwiZXhwIjoxNjQ1MTY5ODU2LCJpYXQiOjE2NDUxNjk1NTZ9.A89vbsesGzVZPv6vWzXwy82w-_yZ6obh7de8tkHNVAvk_uXch7oPJLuOcjr9y6J7qkN_qfJy-ygpKhIidHrYFtOv3j64e0y-TaDWa5TH8jR-CKS2qhbxDUPlvARnoy6ckNCPPGpT4vVLftq9K5kLY8fBFoChXpO4wrNyF5oRlk2k2iPvrFv1R008UDJ-SUFPpoQbRCZyVcmEVgjm0EQxSc-C91FCTUPrnMfha7mLY87pYln1ao7tt04_pPxUh9kz434oiTbIO_Jx0yW1ewrphnI3epWcyTTUcchXlb7_D5HtUfgAme1lbojSn2hzau2yA-r42wKg1nFCBxBfXoWqzw",
      "issuedAt":"2022-02-18T07:32:36.173Z",
      "expiresAt":"2022-02-18T07:37:35.173Z",
      "tokenType":{
        "value":"Bearer"
      },
      "scopes":[
        "message.write"
      ]
    },
    "refreshToken":{
      "tokenValue":"E-Hl84nRNdivT919Z5HTLvWzxE6HMLGJPKpd9GSAr01L5t6Ydd0yu-f2nOYAXl5CqB6-Ope0RnuEtrExvo5do5mpdxTa8T5W3p5f5ic6b5tWzttvROrvyYCss1AK8BPK",
      "issuedAt":"2022-02-18T07:32:36.173Z",
      "expiresAt":null
    }
  },
  "authentication":{
    "authorities":[
      {
        "authority":"ROLE_ANONYMOUS"
      }
    ],
    "details":{
      "remoteAddress":"127.0.0.1",
      "sessionId":"4ECD8EA1920CC2FCC44DC08EEF0A35E3"
    },
    "authenticated":true,
    "principal":"anonymousUser",
    "keyHash":-1462016969,
    "credentials":"",
    "name":"anonymousUser"
  }
}
```

### 二、依次分别启动**oauth2-server**、**oauth2-resource**和**oauth2-client**三个项目

调用 [http://127.0.0.1:8080](http://127.0.0.1:8080)

输入用户名`lalita`和密码`123456`后点击`message.read`

### 三、注意

有3个地方配置`issuer-uri`

`issuer-uri` 尽量用域名 不要用`localhost` 一定不要用IP 而且要和well-known接口中保持一致

这里使用的是 `issuer-uri: http://auth-server:9000`

本地的话需要在 hosts 文件中配置 `127.0.0.1 auth-server`
