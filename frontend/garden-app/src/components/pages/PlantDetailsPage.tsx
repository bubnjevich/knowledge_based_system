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
import {fetchPlantImage, fetchRecommendedPlants, SimilarPlantService} from "../../services/PlantAdviceService";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { RecommendedPlant } from '../../model/RecommendedPlant';
import { ChangeEvent } from 'react'; // Import ChangeEvent type


export const PlantDetailsPage = () => {
    const location = useLocation();
    const { plant } = location.state || {};
    const [plantImage, setPlantImage] = useState<string>('');
    const [recommendedPlants, setRecommendedPlants] = useState<RecommendedPlant[]>([]);
    const [plantImages, setPlantImages] = useState<{ [key: string]: string }>({});
    const [selectedLevel, setSelectedLevel] = useState<string>('1'); // Stanje za praćenje izabrane opcije u drop-down meniju

    useEffect(() => {
        const fetchImage = async () => {
            if (plant) {
                const imageUrl = await fetchPlantImage(plant.name);
                console.log(plant.plantLifespan)
                setPlantImage(imageUrl);
            }
        };

        fetchImage();
    }, [plant]);

    useEffect(() => {
        const fetchRecommended = async () => {
            const plants = await SimilarPlantService.recommendSimilarPlant(plant.name, parseInt(selectedLevel));
            setRecommendedPlants(plants);

            const images: { [key: string]: string } = {};
            for (const plant of plants) {
                const imageUrl = await fetchPlantImage(plant.name);
                images[plant.id] = imageUrl;
            }
            setPlantImages(images);
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

    // Funkcija za promenu izabrane opcije u drop-down meniju
    const handleLevelChange = async (event: SelectChangeEvent<string>) => {
        const selectedLevel = event.target.value as string;
        setSelectedLevel(selectedLevel);
        console.log(selectedLevel);

        try {
            const data = await SimilarPlantService.recommendSimilarPlant(plant.name, parseInt(selectedLevel));
            setRecommendedPlants(data);

            const images: { [key: string]: string } = {};
            for (const plant of data) {
                const imageUrl = await fetchPlantImage(plant.name);
                images[plant.id] = imageUrl;
            }
            setPlantImages(images);
            console.log(data);
        } catch (error) {
            console.error(error);
        }
    };


    return (
        <div style={{ padding: "20px" }}>
            {plant ? (
                <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', backgroundColor: 'rgba(255, 255, 255, 0.8)', padding: 2 }}>
                    <Card sx={{ display: 'flex', backgroundColor: 'rgba(255, 255, 255, 0.8)', maxWidth: '1000px', marginBottom: 4 }}>
                        <CardMedia
                            component="img"
                            alt={plant.name}
                            height="400"
                            image={plantImage || 'default_image_url'}
                            sx={{ width: '400px', objectFit: 'cover' }}
                        />
                        <CardContent sx={{ textAlign: 'left', padding: 2 }}>
                            <Typography variant="h3" component="h1" gutterBottom>
                                {plant.name}
                            </Typography>
                            <Typography variant="body1" component="p">
                                {`Height: ${plant.height}`}
                            </Typography>
                            <Typography variant="body1" component="p">
                                {`Light Hours Needed: ${plant.lightHoursNeeded}`}
                            </Typography>
                            <Typography variant="body1" component="p">
                                {`Lifespan: ${plant.plantLifespan}`}
                            </Typography>
                            <Typography variant="body1" component="p">
                                {`Type: ${plant.plantType}`}
                            </Typography>
                            <Typography variant="body1" component="p">
                                {`Functionalities: ${plant.functionalities.join(', ')}`}
                            </Typography>
                            <Typography variant="body1" component="p">
                                {`Suitable Soils: ${plant.suitableSoils.map((soil: { soilType: string }) => soil.soilType).join(', ')}`}
                            </Typography>
                        </CardContent>
                    </Card>
                    <>
                        {plant.plantLifespan === 'PERENNIAL' && (
                            <>
                    <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: 4 }}>
                        <Typography variant="h6" component="span" sx={{ marginRight: 2 }}>Select Level:</Typography>
                        <Select
                            value={selectedLevel}
                            onChange={handleLevelChange}
                            label="Select Level"
                            sx={{ minWidth: 120 }}
                        >
                            <MenuItem value="1">Level 1</MenuItem>
                            <MenuItem value="2">Level 2</MenuItem>
                            <MenuItem value="3">Level 3</MenuItem>
                        </Select>
                    </Box>
                    <Box sx={{ width: '70%', height: '40%' }}>
                        <Typography variant="h4" component="h2" gutterBottom>
                            {selectedLevel === '1' ? 'Similar flowering perennial' :
                                selectedLevel === '2' ? 'Similar perennial' :
                                    selectedLevel === '3' ? 'Similar plants' : ''}
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
            )}
                    </>

                </Box>
            ) : (
                <Typography variant="h5" component="h2">
                    No plant data available
                </Typography>
            )}
        </div>
    );
};

export default PlantDetailsPage;