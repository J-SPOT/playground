import { useGetArticlesQuery } from '@/apis/article/article.query';
import { ArticleType } from '@/apis/article/types';
import Board from '@/components/board/Board';
import Area from '@/components/layouts/Area';
import { styled } from 'twin.macro';

export default function Home() {
  const { data: articleData, isLoading } = useGetArticlesQuery();

  if (isLoading) {
    return;
  }

  return (
    <Area tw="pt-36 flex flex-col items-center justify-center">
      <MainTitle>Funny Board</MainTitle>
      {articleData &&
        articleData.map((data: ArticleType, index: number) => {
          return (
            <Board
              title={data.title}
              content={data.content}
              key={index}
              tw="mb-10"
            />
          );
        })}
    </Area>
  );
}

const MainTitle = styled.h1`
  font-size: 4rem;
  margin-bottom: 3rem;
`;
