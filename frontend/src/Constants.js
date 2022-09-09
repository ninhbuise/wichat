const prod = {
    app: {
        API_BASE_URL: '',
        OMDB_BASE_URL: ''
    },
    keycloak: {
        KEYCLOAK_BASE_URL: "",
        REALM: "wichat",
        CLIENTID: "login-app"
    }
}

const dev = {
    app: {
        API_BASE_URL: 'http://localhost:8081',
        OMDB_BASE_URL: ''
    },
    keycloak: {
        KEYCLOAK_BASE_URL: "http://localhost:8081",
        REALM: "wichat",
        CLIENTID: "login-app"
    }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod