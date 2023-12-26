import { UseQueryOptions } from '@tanstack/react-query';
import { AxiosError } from 'axios';

export type ArticleType = {
  id: number;
  title: string;
  content: string;
};

export type ArticleQueryType = {
  params?: ArticleType;
  useQueryOptions?: UseQueryOptions<ArticleType, AxiosError>;
};
