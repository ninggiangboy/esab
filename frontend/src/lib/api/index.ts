import type { AxiosInstance } from 'axios'

import { ExampleApi } from './example.api'

/**
 * API class for the application
 * @example
 * const api = new Api(
 * axios.create({
 *   baseURL: 'http://localhost:8080',
 * }),
 * )
 * api.example.hello()
 */
export class Api {
  example: ExampleApi

  constructor(client: AxiosInstance) {
    this.example = new ExampleApi(client)
  }
}
