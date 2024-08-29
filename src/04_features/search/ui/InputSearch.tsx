import React, { useEffect, useState } from 'react';
import { useDebounce } from '@/06_shared/hooks/useDebounce';
import css from '@/06_shared/ui/Input/Input.module.css';
import cn from 'classnames';
import { fetchSearchResults } from '../api/searchApi';
import { InputSearchProps } from '../model/types';


export const InputSearch = ({ maxLength = 50, value, inputStyle = 'default', register, type, onApiResponse }: InputSearchProps) => {
    const [inputValue, setInputValue] = useState(value || '');
    const debouncedValue = useDebounce(inputValue, 500);

    useEffect(() => {
        if (debouncedValue) {
            fetchSearchResults(debouncedValue).then(onApiResponse);
        }
    }, [debouncedValue, onApiResponse]);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setInputValue(event.target.value);
    };

    return (
        <input
            {...register}
            type={type}
            maxLength={maxLength}
            className={cn(css.root, css.input_text)}
            value={inputValue}
            onChange={handleChange}
        />
    );
};
