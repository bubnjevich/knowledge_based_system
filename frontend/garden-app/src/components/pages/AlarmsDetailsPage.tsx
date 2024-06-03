import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Slider from 'react-slick';
import Select, {SelectChangeEvent} from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import {fetchPlantImage, fetchRecommendedPlants, fetchAlarmsPlants} from "../../services/PlantAdviceService";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import {AlarmType, RecommendedPlant} from '../../model/RecommendedPlant';
import { ChangeEvent } from 'react'; // Import ChangeEvent type
import {Warning} from "@mui/icons-material";
import CircularProgress from '@mui/material/CircularProgress';

export const AlarmDetailsPage = () => {
    const [recommendedPlants, setRecommendedPlants] = useState<RecommendedPlant[]>([]);
    const [plantImages, setPlantImages] = useState<{ [key: string]: string }>({});
    const [alarmMessage, setAlarmMessage] = useState<string>("");
    const [alarmType, setAlarmType] = useState<AlarmType | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true); // Dodajemo isLoading stanje

    useEffect(() => {
        const fetchRecommended = async () => {
            setIsLoading(true);
            const plants = await fetchAlarmsPlants();
            setRecommendedPlants(plants.recommendedPlants);
            setAlarmMessage(plants.alarmMessage);
            setAlarmType(plants.alarmType);
            const images: { [key: string]: string } = {};
            for (const plant of plants.recommendedPlants) {
                const imageUrl = await fetchPlantImage(plant.name);
                images[plant.id] = imageUrl;
            }
            setPlantImages(images);

            setIsLoading(false);
        };

        fetchRecommended();
    }, []);

    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 1,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1,
                    infinite: true,
                    dots: true
                }
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    initialSlide: 1
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    };

    return (
        <div style={{ padding: "20px" }}>
            {isLoading ? (
                <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <CircularProgress />
                </div>
            ) : alarmType ? (
                <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', backgroundColor: 'rgba(255, 255, 255, 0.8)', padding: 2 }}>
                    <Card sx={{ display: 'flex', backgroundColor: 'rgba(255, 255, 255, 0.8)', maxWidth: '1000px', marginBottom: 4 }}>
                        <Box sx={{ width: 200, height: 200, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                            <Warning sx={{ fontSize: 100, color: 'red' }} />
                        </Box>
                        <CardContent sx={{ textAlign: 'left', padding: 2 }}>
                            <Typography variant="h4" component="h1" gutterBottom>
                                {alarmMessage}
                            </Typography>
                        </CardContent>
                    </Card>
                    <>
                        <Box sx={{ width: '70%', height: '40%' }}>
                            <Typography variant="h5" component="h2" gutterBottom>
                                We recommended drought resistance plants
                            </Typography>
                            <Slider {...settings}>
                                {recommendedPlants.map((plant: RecommendedPlant) => (
                                    <Card key={plant.id} >
                                        <CardMedia
                                            component="img"
                                            alt={plant.name}
                                            height="140"
                                            image={plantImages[plant.id] || 'default_image_url'}
                                        />
                                        <CardContent sx={{ textAlign: 'left' }}>
                                            <Typography gutterBottom variant="h5" component="div">
                                                {plant.name}
                                            </Typography>
                                            <Typography variant="body2" color="text.secondary">
                                                {`Height: ${plant.height}`}
                                            </Typography>
                                            <Typography variant="body2" color="text.secondary">
                                                {`Light Hours Needed: ${plant.lightHoursNeeded}`}
                                            </Typography>
                                            <Typography variant="body2" color="text.secondary">
                                                {`Lifespan: ${plant.plantLifespan}`}
                                            </Typography>
                                            <Typography variant="body2" color="text.secondary">
                                                {`Type: ${plant.plantType}`}
                                            </Typography>
                                            {/*<Typography variant="body2" color="text.secondary">*/}
                                            {/*    {`Functionalities: ${plant.functionalities.join(', ')}`}*/}
                                            {/*</Typography>*/}
                                            {/*<Typography variant="body2" color="text.secondary">*/}
                                            {/*    {`Suitable Soils: ${plant.suitableSoils.map(soil => soil.soilType).join(', ')}`}*/}
                                            {/*</Typography>*/}
                                        </CardContent>
                                    </Card>
                                ))}
                            </Slider>


                        </Box>

                    </>

                </Box>
            ) : (
                <Typography variant="h5" component="h2" style={{ backgroundColor: 'lightgray', padding: '10px' }}>
                    No detected alarms
                </Typography>
            )}
        </div>
    );
};
export default AlarmDetailsPage;