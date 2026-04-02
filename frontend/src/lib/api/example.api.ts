import { queryOptions } from '@tanstack/vue-query'
import type { AxiosInstance } from 'axios'

import type { ExampleResponse } from './example.type'

export class ExampleApi {
  constructor(private readonly _client: AxiosInstance) {}

  hello() {
    return queryOptions({
      queryKey: ['hello'],
      queryFn: (): Promise<ExampleResponse> => {
        void this._client
        return Promise.resolve({
          message: 'Hello World',
        })
      },
    })
  }
}
