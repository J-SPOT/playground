import { useQuery } from '@tanstack/react-query';
import { API_ENDPOINTS } from '../api-endpoints';
import { getArticles } from './article';

export const useGetArticlesQuery = () => {
  return useQuery({
    queryKey: [`${API_ENDPOINTS.ARTICLES}`],
    queryFn: () => getArticles(),
  });
};
