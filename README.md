# spring-github-login-starter
Starter Spring Boot project for OAuth login with GitHub

Simple starter for a Spring Boot Web application with GitHub OAuth support.

Steps:
1. Create a GitHub App and get the Client ID and Client Secret values. (Specify callback URL as `http://localhost:8080/login/oauth2/code/github` for development, uncheck Web hooks)
2. Add those values in `application.yml`
3. Run the Spring Boot App. You should be able to login with GitHub

This is a super minimal app. Post login, you will be redirected back to the login page, but you can validate the authorized principal is created by accessing the `/user` API. 
