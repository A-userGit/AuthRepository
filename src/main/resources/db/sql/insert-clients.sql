INSERT INTO client_auth (id, client_name, client_id, client_secret, scope, redirect_uri, post_logout_redirect_uri, is_public)
VALUES ('1', 'gateway-service', 'gateway-client', '$2a$10$RcwnYqs7ObbWkyn34wPo.OplnZulQ4TFUcp07NVtx/nsxYbNQjXiW', 'openid profile', 'http://gateway-service/login/oauth2/code/my-oidc-client http://localhost:8085/swagger-ui/oauth2-redirect.html http://localhost:8085/login/oauth2/code/my-oidc-client http://gateway-service/swagger-ui/oauth2-redirect.html', 'http://gateway-service/gateway/logot', false),
    ('2', 'frontend-ui', 'frontend-ui-client', 'none', 'openid profile email offline_access', 'http://localhost http://localhost:5173 http://localhost:5174 http://simple-shop', 'http://gateway-service/gateway/logot', true)
ON CONFLICT (id) DO NOTHING;
