export const getBasicAuthToken = (username: string, password: string): string => {
    return (btoa(`${username}:${password}`));
};