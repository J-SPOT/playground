import axios, {
  AxiosError,
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from 'axios';

import { axiosConfig } from './config';
import { logOnDev } from './utils';

const pureAxios: AxiosInstance = axios.create(axiosConfig);

pureAxios.interceptors.request.use(onRequest);
pureAxios.interceptors.response.use(onResponse, onErrorResponse);

/**
 * axios request
 */
function onRequest(
  config: AxiosRequestConfig,
): Promise<InternalAxiosRequestConfig> {
  const { method, url } = config;

  logOnDev(`🚀 [API] ${method?.toUpperCase()} ${url} | Request`);

  return Promise.resolve(config as InternalAxiosRequestConfig);
}

/**
 * axios response
 */
function onResponse(response: AxiosResponse): AxiosResponse {
  const { method, url } = response.config;
  const { status } = response;

  logOnDev(`🚀 [API] ${method?.toUpperCase()} ${url} | Response ${status}`);

  if (status === 204) {
    return response;
  }

  return response;
}

/**
 * axios error response
 */
function onErrorResponse(error: AxiosError): Promise<AxiosError> {
  if (axios.isAxiosError(error) && error?.response) {
    const { method, url } = error.config as AxiosRequestConfig;
    const { status } = error.response as AxiosResponse;

    logOnDev(
      `⛔ [API] ${method?.toUpperCase()} ${url} | Error ${status} ${
        (error.response as AxiosResponse).data.message
      }`,
    );
  } else if (error instanceof Error && error.name === 'TimeoutError') {
    logOnDev(`⛔ [API] | TimeError 요청 시간이 초과되었습니다.`);
  } else if (error?.response) {
    logOnDev(
      `⛔ [API] | Error ${(error.response as AxiosResponse).data.message}`,
    );
  }

  return Promise.reject(error);
}
export default pureAxios;
