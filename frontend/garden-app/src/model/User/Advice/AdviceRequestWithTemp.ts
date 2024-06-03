export interface AdviceRequestWithTemp {

    plantType: string | null;
    lightHoursNeeded: number;
    plantFunctionality: string[];
    flowerColor: string | null;
    minTemperature: number | null;
    maxTemperature: number | null
}