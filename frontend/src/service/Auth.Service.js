import { post } from "../HttpHelper";
import Keycloak from 'keycloak-js'
import { config } from '../Constants'

const keycloak = new Keycloak({
    url: `${config.keycloak.KEYCLOAK_BASE_URL}`,
    realm: `${config.keycloak.REALM}`,
    clientId: `${config.keycloak.CLIENTID}`
})

class AuthService {

    async login(username, password) {
        post('/login', { 'username': username, 'password': password }).then((response) => {
            localStorage.setItem('token', response.data.access_token)
            keycloak.token = response.data.access_token
          })
        .catch(err => {
           console.log(err)
        })
    }

    logout() {
        localStorage.removeItem("token");
    }

    register(username, email, password) {

    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem("user"));
    }
}

export default new AuthService();
