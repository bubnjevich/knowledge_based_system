export enum PlantType {
    TREE = "TREE",
    SHRUB = "SHRUB",
    FLOWER = "FLOWER",
    HERB = "HERB"
}

export enum PlantLifespan {
    ANNUAL = "ANNUAL",
    PERENNIAL = "PERENNIAL"
}

export enum PlantFunctionality {
    DECORATIVE = "DECORATIVE",
    EDIBLE = "EDIBLE",
    POLLINATOR = "POLLINATOR",
    SHADE_PROVIDER = "SHADE_PROVIDER",
    FRAGRANT = "FRAGRANT",
    DROUGHT_RESISTANCE = "DROUGHT_RESISTANCE",
    SPICE = "SPICE"
}

export enum SoilType {
    SANDY = "SANDY",
    LOAMY = "LOAMY",
    CLAY = "CLAY",
    PEAT="PEAT",
    CHALKY="CHALKY"
}


export interface AdviceRequest {
    plantType: string | null;
    soilType: string[] | null;
    lightHoursNeeded: number;
    plantFunctionality: string[];
    flowerColor: string | null;
    soilPh: number;
}