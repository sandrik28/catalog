import { useEffect, useRef, useState } from 'react';
import { useDebounce } from '@/06_shared/hooks/useDebounce';
import css from '@/06_shared/ui/Input/Input.module.css';
import cn from 'classnames';
import { fetchSearchResults } from '../api/searchApi';
import { InputSearchProps } from '../model/types';
import { Input } from '@/06_shared/ui/Input/Input';
export const InputSearch = ({ value, type, onApiResponse }: InputSearchProps) => {
    
    const [inputValue, setInputValue] = useState(value || '');
    const debouncedValue = useDebounce(inputValue, 500);
    const isRequestSent = useRef(false);
    const previousDebouncedValue = useRef(debouncedValue); 
    useEffect(() => {
        
        if (debouncedValue.trim().length > 0 && debouncedValue !== previousDebouncedValue.current && !isRequestSent.current) {
            isRequestSent.current = true;
            fetchSearchResults(debouncedValue)
                .then(onApiResponse)
                .finally(() => {
                    isRequestSent.current = false;
                });
        }

        previousDebouncedValue.current = debouncedValue;
    }, [debouncedValue, onApiResponse]);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setInputValue(event.target.value);
    };


    return (
        <Input
            type={type}
            value={inputValue}
        />
    );
};
