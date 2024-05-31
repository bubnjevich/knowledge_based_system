import axios from 'axios';
import store from '../auth/store';

const API_BASE_URL: string | undefined = process.env.REACT_APP_API_BASE_URL;
const axiosInstance = axios.create({
    baseURL: API_BASE_URL
});

axiosInstance.interceptors.request.use(
    config => {
        console.log("Interceptor function is called."); // Dodajte ovu liniju

        const token = localStorage.getItem("token");
        console.log("Token from store:", token);
        if (token) {
            console.log("tokenn: ", token)
            config.headers['X-Auth-Token'] = token
        }
        return config;
    },
    error => {
        console.log("erroriccc: ", error);
        return Promise.reject(error);
    }
);

export default axiosInstance;
