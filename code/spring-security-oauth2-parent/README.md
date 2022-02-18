## 启动DEMO

### 一、分别启动**oauth2-server**和**oauth2-client**两个项目

调用 [http://127.0.0.1:8080/oauth2/authorization/lalita](http://127.0.0.1:8080/oauth2/authorization/lalita)

输入用户名`lalita`和密码`123456`后即可返回OAuth2.0 Client的信息：

```json
{
  "oAuth2AuthorizedClient":{
    "clientRegistration":{
      "registrationId":"lalita",
      "clientId":"lalita-client",
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
          "uri":null,
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
            "client_secret_post"
          ],
          "request_uri_parameter_supported":true,
          "subject_types_supported":[
            "public"
          ],
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
      "tokenValue":"eyJraWQiOiJlNjVmMjI4Zi1kYzg0LTQwODUtOWI1Ni1mNmI2ZWM5MWIyM2QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJsYWxpdGEiLCJhdWQiOiJsYWxpdGEtY2xpZW50IiwibmJmIjoxNjQ1MDg2NjYzLCJzY29wZSI6WyJtZXNzYWdlLndyaXRlIl0sImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo5MDAwIiwiZXhwIjoxNjQ1MDg2OTYzLCJpYXQiOjE2NDUwODY2NjN9.OVnJWxaHyMBCTb2f9JVgjddsW_LWvW3SqIR_Apxp-x3G5Vi_qc1Qn5tzm5YCtk0202BbW5yOwjYieMgVN4LqeUwfS869s88nYl0cqjlHYaZbmrN5Yc9-gHfkHzGZ6VW-wX1I6Zq5NOFEpUgz4F00GazmO8BCLVClUqcygLEavP-r2K3OtzlSVqY1J_KsP7qfHd9CvI5EGCaDXeIYmwArYrV-tcUUlgbCMMHxtN2NPpptFEHldzP6-nlDzEffbIPhZtPmIIefFjJ4LrlbQ5ezaQB4JBpFt_7EIWB483VRkvOJ7l_dBrqm-NCp_SDLXMXYF8OR3NfanHpdBMCaYT_cSA",
      "issuedAt":"2022-02-17T08:31:03.813Z",
      "expiresAt":"2022-02-17T08:36:03.813Z",
      "tokenType":{
        "value":"Bearer"
      },
      "scopes":[
        "message.write"
      ]
    },
    "refreshToken":{
      "tokenValue":"OOo4Ht5wkse2WWUsZzchk2iZcClemCpGGvdttPJHg8dOVvBkmHaNsFr8z2mRw9OTeB-pCR-8GdUadsi0auOh9ane23dRq6q2M1IVfTRJGq_weMNPyhDqO0nUjk-WRwtf",
      "issuedAt":"2022-02-17T08:31:03.813Z",
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
      "sessionId":"928207FCB616B258D154CE626D5E0D31"
    },
    "authenticated":true,
    "principal":"anonymousUser",
    "keyHash":-1993719621,
    "credentials":"",
    "name":"anonymousUser"
  }
}
```

### 二、注意

provider 尽量用域名 不要用localhost 一定不要用IP 而且要和well-known接口中保持一致

