import React from 'react';
import { styled, theme } from 'twin.macro';

interface Props {
  className?: string;
  children: React.ReactNode;
}

export default function Area({ className, children }: Props) {
  return <AreaContainer className={className}>{children}</AreaContainer>;
}

const AreaContainer = styled.div`
  width: 100%;
  padding: 0 ${theme`spacing.gutter`};
  margin: 0 auto;
`;
