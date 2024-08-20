import { configureStore } from '@reduxjs/toolkit';
import sessionReducer from '../05_entities/session/sessionSlice';

export const store = configureStore({
  reducer: {
    session: sessionReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;