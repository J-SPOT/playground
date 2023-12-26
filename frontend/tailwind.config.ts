import type { Config } from 'tailwindcss';

const breakpoint = require('./tailwind-theme/breakpoint');

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      screens: breakpoint,
      spacing: {
        gutter: '20px',
      },
    },
  },
  plugins: [],
};

export default config;
