import { useEffect, useRef, useState } from 'react';
import { useDebounce } from '@/06_shared/hooks/useDebounce';
import css from '@/06_shared/ui/Input/Input.module.css';
import cn from 'classnames';
import { fetchSearchResults } from '../api/searchApi';
import { InputSearchProps } from '../model/types';
import debounce from 'debounce';



export const InputSearch = ({ maxLength = 50, value, type, onApiResponse }: InputSearchProps) => {
    const debouncedSearch = debounce((searchTerm) => {
        // Perform the search or API call with searchTerm
    }, 500);

    // const handleInputChange = (event) => {
    //     setInputValue(event.target.value);
    //     debouncedSearch(event.target.value);
    // };

    const [inputValue, setInputValue] = useState(value || '');
    const debouncedValue = useDebounce(inputValue, 500);
    const isRequestSent = useRef(false);
    const previousDebouncedValue = useRef(debouncedValue);

    useEffect(() => {
        if (debouncedValue !== previousDebouncedValue.current && !isRequestSent.current) {
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
        <input
            id={"search"}
            type={type}
            maxLength={maxLength}
            className={cn(css.root, css.input_text)}
            value={inputValue}
            onChange={handleChange}
        />
    );
};
