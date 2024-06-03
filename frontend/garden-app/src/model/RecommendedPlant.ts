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

export enum AlarmType {
    DROUGHT = "DROUGHT",
    ECOSYSTEM_DISBALANCE = "ECOSYSTEM_DISBALANCE",
    LOW_SOIL_MOISTURE = "LOW_SOIL_MOISTURE",
    PEST_INFESTATION = "PEST_INFESTATION",
    OTHER = "OTHER"
}

export interface RecommendedPlantsForAlarms {

    alarmType : AlarmType,
    alarmMessage: string,
    recommendedPlants: RecommendedPlant[]
}