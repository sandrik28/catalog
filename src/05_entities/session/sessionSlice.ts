import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface SessionState {
  userId: string | null;
  role: string | null;
}


const initialState: SessionState = {
  userId: "0",
  role: "user",
};


const sessionSlice = createSlice({
  name: 'session',
  initialState,
  reducers: {
    setSession: (state, action: PayloadAction<{ userId: string; role: string }>) => {
      state.userId = action.payload.userId;
      state.role = action.payload.role;
    },
    clearSession: (state) => {
      state.userId = null;
      state.role = null;
    },
  },
});

export const { setSession, clearSession } = sessionSlice.actions;

export default sessionSlice.reducer;