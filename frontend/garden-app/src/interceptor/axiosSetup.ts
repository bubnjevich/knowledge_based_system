import axios from 'axios';
import store from '../auth/store';

const API_BASE_URL: string | undefined = process.env.REACT_APP_API_BASE_URL;
const axiosInstance = axios.create({
    baseURL: API_BASE_URL
});

axiosInstance.interceptors.request.use(
    config => {
        const token = store.getState().user.token;
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
