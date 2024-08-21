import { configureStore } from '@reduxjs/toolkit';
import sessionReducer from '@/05_entities/session/sessionSlice';
import { wishlistReducer } from '@/05_entities/wishlist/model/slice';

export const store = configureStore({
  reducer: {
    session: sessionReducer,
    wishlist: wishlistReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
