import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Roles } from '../user/api/types';

export interface SessionState {
  userId: number | null;
  role: string | null;
}

const initialState: SessionState = {
  userId: 1,
  role: "ROLE_USER" as Roles,
};

// const initialState: SessionState = {
//   userId: 2,
//   role: "ROLE_ADMIN" as Roles,
// };


const sessionSlice = createSlice({
  name: 'session',
  initialState,
  reducers: {
    setSession: (state, action: PayloadAction<SessionState>) => {
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
