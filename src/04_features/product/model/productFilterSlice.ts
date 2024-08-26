import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface ProductFilterState {
    filter: 'all' | 'favorites';
}

const initialState: ProductFilterState = {
    filter: 'all',
};

const productFilterSlice = createSlice({
    name: 'productFilter',
    initialState,
    reducers: {
        setFilter(state, action: PayloadAction<'all' | 'favorites'>) {
            state.filter = action.payload;
        },
    },
});

export const { setFilter } = productFilterSlice.actions;

export default productFilterSlice.reducer;
