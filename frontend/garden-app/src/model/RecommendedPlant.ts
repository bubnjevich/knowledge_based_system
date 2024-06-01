import {PlantFunctionality, PlantLifespan, PlantType, SoilType} from "./User/Advice/AdviceRequest";

export interface Soil {
    id: number,
    soilType: SoilType

}


export interface RecommendedPlant {
    id: number;
    name: string;
    plantLifespan: PlantLifespan;
    plantType: PlantType;
    functionalities: PlantFunctionality[];
    suitableSoils: Soil[];
    height: number;
    lightHoursNeeded: number;

}