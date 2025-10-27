INSERT INTO client_auth (id, client_name, client_id, client_secret, scope, redirect_uri, post_logout_redirect_uri)
VALUES ('1', 'gateway-service', 'gateway-client', '$2a$10$RcwnYqs7ObbWkyn34wPo.OplnZulQ4TFUcp07NVtx/nsxYbNQjXiW', 'openid profile', 'http://gateway-service:8084/login/oauth2/code/my-oidc-client http://gateway-service:8084/swagger-ui/oauth2-redirect.html', 'http://gateway-service:8084/gateway/logot')
ON CONFLICT (id) DO NOTHING;
