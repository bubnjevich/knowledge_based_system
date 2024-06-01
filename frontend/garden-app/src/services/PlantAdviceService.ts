import {AdviceRequest} from "../model/User/Advice/AdviceRequest";
import axios from "axios";
import axiosInstance from "../interceptor/axiosSetup";
import {RecommendedPlant} from "../model/RecommendedPlant";
const API_BASE_URL: string | undefined = process.env.REACT_APP_API_BASE_URL;

export const submitPlantData = async (plantData: AdviceRequest): Promise<RecommendedPlant[]> => {
    try {
        console.log(plantData);
        const response = await axiosInstance.put(`${API_BASE_URL}/advices/fullInfo`, plantData);
        console.log('Data successfully submitted:', response.data);
        return response.data;

    } catch (error) {
        console.error('Error submitting data:', error);
        throw error;
    }
};


const UNSPLASH_ACCESS_KEY = 'kwskgXdQvRPKEc6RmJExM_ZhuMhDLXN9u6dmRzsX9IY'; // Replace with your Unsplash Access Key

export const fetchPlantImage = async (plantName: string): Promise<string> => {
    try {
        const response = await axios.get('https://api.unsplash.com/search/photos', {
            params: {
                query: plantName,
                client_id: UNSPLASH_ACCESS_KEY,
                per_page: 1,
            },
        });

        if (response.data.results.length > 0) {
            return response.data.results[0].urls.small;
        } else {
            return 'default_image_url'; // Provide a default image URL if no result found
        }
    } catch (error) {
        console.error('Error fetching plant image:', error);
        return 'default_image_url'; // Provide a default image URL in case of error
    }
};
