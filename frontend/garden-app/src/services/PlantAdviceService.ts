import {AdviceRequest} from "../model/User/Advice/AdviceRequest";
import axios from "axios";
import axiosInstance from "../interceptor/axiosSetup";
const API_BASE_URL: string | undefined = process.env.REACT_APP_API_BASE_URL;

export const submitPlantData = async (plantData: AdviceRequest): Promise<void> => {
    try {
        const response = await axiosInstance.put(`${API_BASE_URL}/advices/fullInfo`, plantData);
        console.log('Data successfully submitted:', response.data);
    } catch (error) {
        console.error('Error submitting data:', error);
        throw error;
    }
};