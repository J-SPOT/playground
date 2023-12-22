import invariant from 'tiny-invariant';

/**
 * .env파일 에러체크
 */
export const checkEnvError = () => {
  invariant(
    process.env.NEXT_PUBLIC_API_ENDPOINT,
    'NEXT_PUBLIC_API_ENDPOINT is not defined, please define it in your .env file',
  );
};

/**
 * API 호출 로그(개발모드에서만 노출)
 */
export const logOnDev = (message: string) => {
  if (process.env.NEXT_PUBLIC_RUN_MODE === 'development') {
    console.log(message);
  }
};
