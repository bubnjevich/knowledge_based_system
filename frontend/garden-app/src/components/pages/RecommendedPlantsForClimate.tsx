import React from "react";
import Container from "@mui/material/Container";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";
import FormControl from "@mui/material/FormControl";
import { Grass } from "@mui/icons-material";
import { AdviceRequest } from "../../model/User/Advice/AdviceRequest";
import {submitPlantData, submitPlantDataWithClimateConditions} from "../../services/PlantAdviceService";
import { useNavigate } from "react-router-dom";
import {AdviceRequestWithTemp} from "../../model/User/Advice/AdviceRequestWithTemp";

const plantTypes = ["TREE", "SHRUB", "FLOWER", "HERB"];
const soilTypes = ["SANDY", "LOAMY", "CLAY", "PEAT", "CHALKY"];
const functionalities = ["DECORATIVE", "EDIBLE", "POLLINATOR", "SHADE_PROVIDER", "FRAGRANT", "DROUGHT_RESISTANCE", "SPICE"];
const flowerColors = ["RED", "BLUE", "YELLOW", "PURPLE", "WHITE", "GREEN", "PINK"];

export default function RecommendedPlantsForClimate() {
    const [plantType, setPlantType] = React.useState<string | null>(null);
    const [soilType, setSoilType] = React.useState<string[]>([]);
    const [lightHours, setLightHours] = React.useState<number>(0);
    const [selectedFunctionalities, setSelectedFunctionalities] = React.useState<string[]>([]);
    const [selectedColor, setSelectedColor] = React.useState<string | null>(null);
    const [soilPh, setSoilPh] = React.useState<number>(0);
    const [minTemp, setMinTemp] = React.useState<number | null>(null);
    const [maxTemp, setMaxTemp] = React.useState<number | null>(null);

    const navigate = useNavigate();

    const formData: AdviceRequestWithTemp = {
        plantType: plantType,
        lightHoursNeeded: lightHours,
        plantFunctionality: selectedFunctionalities,
        flowerColor: selectedColor,
        minTemperature: minTemp,
        maxTemperature: maxTemp,
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const recommended = await submitPlantDataWithClimateConditions(formData);
            navigate('/recommended', { state: { recommendedPlants: recommended } });
        } catch (error) {
            alert('Failed to submit data');
        }
    };

    return (
        <Container component="main" maxWidth="xs" style={{ background: 'rgba(255, 255, 255, 0.9)' }}>
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                    <Grass />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Environmental Conditions Form
                </Typography>
                <Box component="form" noValidate sx={{ mt: 1 }} onSubmit={handleSubmit}>
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="plant-type-label">Plant Type</InputLabel>
                        <Select
                            labelId="plant-type-label"
                            id="plant-type"
                            value={plantType}
                            onChange={(e) => {
                                const selectedValues = e.target.value as string;
                                setPlantType(selectedValues);
                            }}
                            label="Plant Type"
                        >
                            {plantTypes.map((type) => (
                                <MenuItem key={type} value={type}>{type}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="light-hours"
                        label="Garden exposure to the sun"
                        type="number"
                        value={lightHours}
                        onChange={(e) => setLightHours(parseInt(e.target.value))}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="min-temp"
                        label="Minimum Temperature"
                        type="number"
                        inputProps={{ step: 0.1 }}
                        value={minTemp ?? ""}
                        onChange={(e) => setMinTemp(parseFloat(e.target.value))}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="max-temp"
                        label="Maximum Temperature"
                        type="number"
                        inputProps={{ step: 0.1 }}
                        value={maxTemp ?? ""}
                        onChange={(e) => setMaxTemp(parseFloat(e.target.value))}
                    />
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="functionalities-label">Plant Functionality</InputLabel>
                        <Select
                            labelId="functionalities-label"
                            id="functionalities"
                            multiple
                            value={selectedFunctionalities}
                            onChange={(e) => setSelectedFunctionalities(e.target.value as string[])}
                        >
                            {functionalities.map((func) => (
                                <MenuItem key={func} value={func}>{func}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="flower-colors-label">Flower Color</InputLabel>
                        <Select
                            labelId="flower-colors-label"
                            id="flower-colors"
                            value={selectedColor}
                            onChange={(e) => setSelectedColor(e.target.value)}
                        >
                            {flowerColors.map((color) => (
                                <MenuItem key={color} value={color}>{color}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Submit
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}
