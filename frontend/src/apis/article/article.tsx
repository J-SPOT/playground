import pureAxios from '../axios/pureAxios';
import { ArticleType } from './types';
import { API_ENDPOINTS } from '../api-endpoints';

export const getArticles = async () => {
  const res = await pureAxios.get(`${API_ENDPOINTS.ARTICLES}`);
  return res.data;
};

export const postArticles = async (body: ArticleType) => {
  const res = await pureAxios.post(`${API_ENDPOINTS.ARTICLES}`, body);
  return res.data;
};

export const getArticlesById = async ({
  id,
}: ArticleType): Promise<ArticleType> => {
  const params = { id };
  const res = await pureAxios.get(`${API_ENDPOINTS.ARTICLES}`, {
    params,
  });
  return res.data;
};
