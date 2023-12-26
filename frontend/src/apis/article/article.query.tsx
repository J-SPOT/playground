import { useQuery } from '@tanstack/react-query';
import { API_ENDPOINTS } from '../api-endpoints';
import { getArticles } from './article';
import { ArticleType } from './types';
import { AxiosError } from 'axios';

/**
 * 게시글 목록 조회 쿼리
 */
export const useGetArticlesQuery = () => {
  return useQuery<ArticleType[], AxiosError>({
    queryKey: [API_ENDPOINTS.ARTICLES],
    queryFn: () => getArticles(),
  });
};
