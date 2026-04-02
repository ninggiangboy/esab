/// <reference types="vite/client" />

declare module 'nprogress' {
  interface NProgress {
    configure(options: { showSpinner?: boolean }): void
    start(): void
    done(force?: boolean): void
  }
  const nprogress: NProgress
  export default nprogress
}
