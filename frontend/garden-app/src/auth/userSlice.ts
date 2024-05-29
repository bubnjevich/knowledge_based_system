import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface UserState {
    token: string | null;
    role: string;
    email: string; // Added email field
}

const initialState: UserState = {
    token: null,
    role: "",
    email: "", // Initial email state
};

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setToken: (state, action: PayloadAction<string>) => {
            state.token = action.payload;
        },
        setUserRole: (state, action: PayloadAction<string>) => {
            state.role = action.payload;
        },
        setEmail: (state, action: PayloadAction<string>) => { // New reducer for setting email
            state.email = action.payload;
        },
        logout: (state) => {
            state.token = null;
            state.role = "";
            state.email = ""; // Reset email on logout
        },
    },
});

// Export the new action
export const { setToken, setUserRole, setEmail, logout } = userSlice.actions;

export default userSlice.reducer;
