import { z } from 'zod'

/** Same idea as the sample’s `schema.validateFn()` helper, without fragile prototype patching on Zod 4. */
export function validateFn<S extends z.ZodType>(schema: S) {
  return (value: unknown): string | true => {
    const result = schema.safeParse(value)
    if (result.success) return true
    return result.error.issues[0]?.message ?? ''
  }
}

export { z }
