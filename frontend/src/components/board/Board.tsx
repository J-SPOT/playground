import React from 'react';
import { styled } from 'twin.macro';

type boardProps = {
  title: string;
  content: string;
  className?: string;
};

export default function Board({ title, content, className }: boardProps) {
  return (
    <>
      <BoardContainer className={className}>
        <Title>{title}</Title>
        <Contents>{content}</Contents>
      </BoardContainer>
    </>
  );
}

const BoardContainer = styled.div`
  border-radius: 2rem;
  border: 1px solid gray;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  width: 100%;
  padding: 1rem;
`;

const Title = styled.h1`
  font-size: 2rem;
`;

const Contents = styled.h2`
  font-size: 1rem;
`;
