export type UserRole = 'moderator' | 'productOwner';

export interface User {
  id: number;
  name: string;
  role: UserRole;
}

export interface AuthState {
  isAuthenticated: boolean;
  user: User | null;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}



export enum Roles {
  ROLE_ADMIN = 'ROLE_ADMIN',
  ROLE_USER = 'ROLE_USER',

}

export interface UserDto {
  id: number;
  name: string;
  password: string;
  email: string;
  idOfFollowedProductsList: number[]; // List<Long> в Java аналогично массиву чисел в TypeScript
  role: Roles;
}