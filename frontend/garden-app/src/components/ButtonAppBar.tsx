import React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { useNavigate } from 'react-router-dom';

interface ButtonAppBarProps {
    setIsLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
}

const ButtonAppBar: React.FC<ButtonAppBarProps> = ({ setIsLoggedIn }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("token");
        setIsLoggedIn(false);
        navigate("/");
    };

    const handleMainPage = () => {
        navigate("/mainPage")
    }


    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" sx={{ background: 'rgba(255, 255, 255, 0.9)' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ color: 'purple', cursor: 'pointer'}}
                    onClick={handleMainPage}>
                        Main Page
                    </Typography>
                    <Typography variant="h6" component="div" sx={{ marginLeft: 2,  color: 'purple', cursor: 'pointer'}}
                                onClick={() => navigate("/alarms")}>
                        Alarms
                    </Typography>
                    <Typography variant="h6" component="div" sx={{ marginLeft: 2,  color: 'purple', cursor: 'pointer'}}
                                onClick={() => navigate("/sowing")}>
                        Sowing
                    </Typography>
                    <Typography variant="h6" component="div" sx={{ marginLeft: 2,  color: 'purple', cursor: 'pointer'}}
                                onClick={() => navigate("/climate")}>
                        Garden
                    </Typography>
                    <Box sx={{ flexGrow: 1 }} />
                    <Typography
                        variant="h6"
                        component="div"
                        sx={{ color: 'purple', cursor: 'pointer' }}
                        onClick={handleLogout}
                    >
                        Logout
                    </Typography>
                </Toolbar>
            </AppBar>
        </Box>
    );
};

export default ButtonAppBar;
