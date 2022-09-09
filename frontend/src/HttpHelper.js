import axios from 'axios';
import { config } from './Constants'

const url = config.app.API_BASE_URL;
const token = localStorage.getItem('token');
const headers = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`,
    'Access-Control-Allow-Origin': '*'
}

export function get(endpoint) {
    return axios.get(url + endpoint, { headers });
}

export function put(endpoint, body) {
    return axios.put(url + endpoint, body, { headers });
}

export async function post(endpoint, body) {
    const data = await axios.post(url + endpoint, body, headers)
    return data
}

export function del(endpoint) {
    return axios.delete(url + endpoint, { headers });
}

export function patch(endpoint, body) {
    return axios.patch(url + endpoint, body, { headers });
}