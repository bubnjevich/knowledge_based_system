import axios, { AxiosResponse } from "axios";
import {UserRequest, LoginRequest} from "../model/User/UserRequest";

const API_BASE_URL: string | undefined = process.env.REACT_APP_API_BASE_URL;

const login = async (loginRequest: {
    password: string;
    email: string
}) : Promise<string> => {

    const response: AxiosResponse<string> = await axios.post(
        `${API_BASE_URL}/user/login`,
        loginRequest
    );
    return response.data;

}


const register = async (userRequest: {
    name: string;
    lastName: string;
    password: string;
    email: string
}): Promise<UserRequest> => {
    console.log(`${API_BASE_URL}/user/register`);
    console.log(userRequest);
    const response: AxiosResponse<UserRequest> = await axios.post(
        `${API_BASE_URL}/user/register`,
        userRequest,
        {
            headers: {
                "Content-Type": "application/json",
            },
        }
    );
    return response.data;
};

export const userService = {
    login,
    register
};


export default userService;