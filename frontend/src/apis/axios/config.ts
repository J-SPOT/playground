export const axiosConfig = {
  baseURL: process.env.NEXT_PUBLIC_API_ENDPOINT,
  timeout: 10 * 1000, // 10초
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
};
