declare const _brand: unique symbol;

declare global {
    export type Phone = string

    export type Email = string
  
    export type Brand<K, T> = K & { [_brand]: T };
    
    export type Id = number
  
    export type Url = string
}


export {}