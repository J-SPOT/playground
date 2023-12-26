import Area from '@/components/layouts/Area';
import { styled } from 'twin.macro';

export default function Home() {
  return (
    <Area>
      <MainTitle>메인</MainTitle>
    </Area>
  );
}

const MainTitle = styled.h1`
  font-size: 4rem;
`;
