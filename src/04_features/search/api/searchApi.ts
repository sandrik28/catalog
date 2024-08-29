import { Status } from '@/05_entities/product/model/types';
import { productsMockCards } from '@/06_shared/lib/server/';
export async function fetchSearchResults(query: string) {
  try {
    //   const response = await fetch(`https://api.example.com/search?q=${query}`);
    //   return await response.json();

    return productsMockCards.map(product => ({
      ...product,
      status: product.status as Status,
      timeOfLastApproval: new Date(product.timeOfLastApproval),
    })).filter(product => product.title.includes(query));
  } catch (error) {
    console.error('Error fetching data:', error);
    throw error;
  }
}
