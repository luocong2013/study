# Keycloak

## Before you start

Make sure you have Docker installed.

## Start Keycloak

From a terminal start Keycloak with the following command:

```bash
docker run -d -p 7777:8080 --name keycloak-server -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:17.0.0 start-dev
```

This will start Keycloak exposed on the local port 8080. It will also create an initial admin user with username `admin` and password `admin`.

## Login to the admin console

Go to the [Keycloak Admin Console](http://localhost:8080/admin) and login with the username and password you created earlier.

## Create a realm

A realm in Keycloak is the equivalent of a tenant. It allows creating isolated groups of applications and users. By default there is a single realm in Keycloak called `master`. This is dedicated to manage Keycloak and should not be used for your own applications.

Let’s create our first realm.

1. Open the [Keycloak Admin Console](http://localhost:8080/admin)
2. Hover the mouse over the dropdown in the top-left corner where it says `Master`, then click on `Add realm`
3. Fill in the form with the following values:
   - Name: `myrealm`
4. Click `Create`

![Add Realm](https://www.keycloak.org/resources/images/guides/add-realm.png)

## Create a user

Initially there are no users in a new realm, so let’s create one:

1. Open the [Keycloak Admin Console](http://localhost:8080/admin)
2. Click `Users` (left-hand menu)
   - Click `Add user` (top-right corner of table)
3. Fill in the form with the following values:
   - Username: `myuser`
   - First Name: Your first name
   - Last Name: Your last name
4. Click `Save`

![Add User](https://www.keycloak.org/resources/images/guides/add-user.png)

The user will need an initial password set to be able to login. To do this:

1. Click `Credentials` (top of the page)
2. Fill in the `Set Password` form with a password
3. Click `ON` next to `Temporary` to prevent having to update password on first login

![Set Password](https://www.keycloak.org/resources/images/guides/set-password.png)

## Login to account console

Let’s now try to login to the account console to verify the user is configured correctly.

1. Open the [Keycloak Account Console](http://localhost:8080/realms/myrealm/account)
2. Login with `myuser` and the password you created earlier

You should now be logged-in to the account console where users can manage their accounts.

![Keycloak Account Console](https://www.keycloak.org/resources/images/guides/account-console.png)

## Secure your first app

Let’s try to secure our first application. First step is to register this application with your Keycloak instance:

1. Open the [Keycloak Admin Console](http://localhost:8080/admin)
2. Click 'Clients'
3. Fill in the form with the following values:
   - Client ID: `myclient`
   - Client Protocol: `openid-connect`
   - Root URL: `https://www.keycloak.org/app/`
4. Click `Save`

![Add Client](https://www.keycloak.org/resources/images/guides/add-client.png)

To make it easy for you we have a SPA testing application available on the [Keycloak website](https://www.keycloak.org/app/).

Open https://www.keycloak.org/app/ and click `Save` to use the default configuration.

Now you can click `Sign in` to authenticate to this application using the Keycloak server you started earlier.

## Next

Before you go and run Keycloak in production there are a few more things that you will want to do, including:

- Switch to a production ready database such as PostgreSQL
- Configure SSL with your own certificates
- Switch the admin password to a more secure password

For more information check out the [server guides](https://www.keycloak.org/guides#server).