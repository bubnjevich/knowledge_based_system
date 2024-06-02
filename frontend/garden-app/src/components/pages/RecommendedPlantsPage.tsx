import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Grid, Pagination } from '@mui/material';
import { RecommendedPlant } from '../../model/RecommendedPlant';
import {fetchPlantImage} from "../../services/PlantAdviceService";
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';

const RecommendedPlantsPage = () => {
    const location = useLocation();
    const { recommendedPlants } = location.state || { recommendedPlants: [] };
    const [currentPage, setCurrentPage] = useState(1);
    const [plantImages, setPlantImages] = useState<{ [key: string]: string }>({});
    const itemsPerPage = 6; // Adjust as needed
    const pageCount = Math.ceil(recommendedPlants.length / itemsPerPage);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchImages = async () => {
            const images: { [key: string]: string } = {};
            for (const plant of recommendedPlants) {
                const imageUrl = await fetchPlantImage(plant.name);
                images[plant.id] = imageUrl;
            }
            setPlantImages(images);
        };

        fetchImages();
    }, [recommendedPlants]);

    const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
        setCurrentPage(value);
    };

    const currentPlants = recommendedPlants.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);


    const handleCardClick = (plant: RecommendedPlant) => {
        navigate('/plant-details', { state: { plant } });
    };

    return (
        <div style={{ padding: "20px"}}>
            <div style={{width: "80%", margin: "auto"}}>
            <Grid container spacing={2} style={{ backgroundColor: "rgba(255, 255, 255, 0.8)"}}>
                    {currentPlants.map((plant: RecommendedPlant) => (
                        <Grid item xs={12} sm={6} md={4} key={plant.id}>
                            <Box
                                sx={{
                                    cursor: 'pointer',
                                    '&:hover': {
                                        transform: 'scale(1.05)',
                                        transition: 'transform 0.2s',
                                    },
                                }}
                                onClick={() => handleCardClick(plant)}
                                >
                            <Card sx={{ minWidth: 300, maxWidth: 400 }}>
                                <CardMedia
                                    component="img"
                                    alt={plant.name}
                                    height="140"
                                    width="100%"
                                    style={{ maxWidth: "100%" }}
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
                                    <Typography variant="body2" color="text.secondary">
                                        {`Functionalities: ${plant.functionalities.join(', ')}`}
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary">
                                        {`Suitable Soils: ${plant.suitableSoils.map(soil => soil.soilType).join(', ')}`}
                                    </Typography>
                                </CardContent>
                                <CardActions>
                                    <Button size="small">Share</Button>
                                    <Button size="small">Learn More</Button>
                                </CardActions>
                            </Card>
                            </Box>
                            </Grid>
                    ))}
                </Grid>

                <Grid container justifyContent="center" sx={{ mt: 2 }}>
                    <Pagination
                        count={pageCount}
                        page={currentPage}
                        onChange={handlePageChange}
                        showFirstButton
                        showLastButton
                        sx={{
                            '& .MuiPaginationItem-root': {
                                color: 'white', // Change text color to white
                            },
                            '& .MuiPaginationItem-page': {
                                backgroundColor: '#1976d2', // Change background color of page items
                                '&.Mui-selected': {
                                    backgroundColor: '#1976d2', // Change background color of selected item
                                    color: 'white', // Ensure text color of selected item is white
                                },
                            },
                            '& .MuiPaginationItem-ellipsis': {
                                color: 'white', // Change color of ellipsis
                            },
                            '& .MuiPaginationItem-text': {
                                color: 'white', // Change color of text
                            },
                        }}
                    />
                </Grid>
            </div>
        </div>
    );
};

export default RecommendedPlantsPage;
