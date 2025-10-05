INSERT INTO client_auth (id, client_name, client_id, client_secret, scope, redirect_uri, post_logout_redirect_uri)
VALUES ('1', 'user-service', 'user-client', '$2a$10$RcwnYqs7ObbWkyn34wPo.OplnZulQ4TFUcp07NVtx/nsxYbNQjXiW', 'openid profile', 'http://user-service:8080/login/oauth2/code/my-oidc-client http://user-service:8080/swagger-ui/oauth2-redirect.html', 'http://user-service:8080/users/logot')
ON CONFLICT (id) DO NOTHING;
