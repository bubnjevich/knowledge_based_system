import React from "react";
import Container from "@mui/material/Container";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import MenuItem from "@mui/material/MenuItem";
import Select, {SelectChangeEvent} from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";
import FormControl from "@mui/material/FormControl";
import { Grass } from "@mui/icons-material";
import {AdviceRequest, PlantType} from "../../model/User/Advice/AdviceRequest";
import {submitPlantData} from "../../services/PlantAdviceService";
import {useNavigate} from "react-router-dom";

const plantTypes = ["TREE", "SHRUB", "FLOWER", "HERB"];
const soilTypes = ["SANDY", "LOAMY", "CLAY", "PEAT", "CHALKY"];
const functionalities = ["DECORATIVE", "EDIBLE", "POLLINATOR", "SHADE_PROVIDER", "FRAGRANT", "DROUGHT_RESISTANCE", "SPICE"];
const flowerColors = ["RED", "BLUE", "YELLOW", "PURPLE", "WHITE", "GREEN", "PINK"];

export default function MainFormPage() {
    const [plantType, setPlantType] = React.useState<string | null>(null);
    const [soilType, setSoilType] = React.useState<string[]>([]);
    const [lightHours, setLightHours] = React.useState<number>(0);
    const [selectedFunctionalities, setSelectedFunctionalities] = React.useState<string[]>([]);
    const [selectedColor, setSelectedColor] = React.useState<string | null>(null);
    const [soilPh, setSoilPh] = React.useState<number>(0);

    const [formData, setFormData] = React.useState<AdviceRequest>({
        plantType: plantType,
        soilType: soilType,
        lightHoursNeeded: lightHours,
        plantFunctionality: selectedFunctionalities,
        flowerColor: selectedColor,
        soilPh: soilPh
    });
    const navigate = useNavigate();

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(formData);
        try {
            const recommended = await submitPlantData(formData);
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
                    Plant Recommendation
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
                                setFormData({
                                    ...formData,
                                    plantType: selectedValues
                                });
                            }}
                            label="Plant Type"
                        >
                            {plantTypes.map((type) => (
                                <MenuItem key={type} value={type}>{type}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="soil-type-label">Soil Type</InputLabel>
                        <Select
                            labelId="soil-type-label"
                            id="soil-type"
                            value={soilType}
                            multiple={true}
                            onChange={(e) => {
                                const soilTypes = e.target.value as string[];
                                setSoilType(soilTypes);
                                setFormData({
                                ...formData,
                                soilType: soilTypes
                            });}
                        }
                            label="Soil Type"
                        >
                            {soilTypes.map((type) => (
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
                        onChange={(e) => {
                            setLightHours(Number(e.target.value));

                            setFormData({
                                ...formData,
                                lightHoursNeeded: Number(e.target.value)
                            })
                        }}
                    />
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="functionalities-label">Plant Functionality</InputLabel>
                        <Select
                            labelId="functionalities-label"
                            id="functionalities"
                            multiple={true}
                            value={selectedFunctionalities}
                            onChange={(e) => {
                                const selectedValues = e.target.value as string[];
                                setSelectedFunctionalities(selectedValues);
                                setFormData({
                                    ...formData,
                                    plantFunctionality: selectedValues
                                });
                            }}
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
                            onChange={(e) => {
                                setSelectedColor(e.target.value)
                                setFormData({
                                    ...formData,
                                    flowerColor: e.target.value
                                });

                            }}
                        >
                            {flowerColors.map((color) => (
                                <MenuItem key={color} value={color}>{color}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="soil-ph"
                        label="Soil pH"
                        type="number"
                        value={soilPh}
                        onChange={(e) => {setSoilPh(Number(e.target.value))

                            setFormData({
                                ...formData,
                                soilPh: Number(e.target.value)
                            });
                        }}
                    />
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
