import { ref, shallowRef } from 'vue'

export type ConfirmAction = {
  label: string
  onClick: () => void | Promise<void>
}

export type ConfirmOptions = {
  title: string
  description?: string
  action?: ConfirmAction
  cancel?: ConfirmAction
  variant?: 'default' | 'destructive'
}

/** Open state for `UiConfirmDialog` (mounted once in `App.vue`). */
export const confirmDialogOpen = ref(false)

/** Payload for the open dialog; cleared when closed. */
export const confirmDialogPayload = shallowRef<ConfirmOptions | null>(null)

export function confirm(data: ConfirmOptions) {
  confirmDialogPayload.value = data
  confirmDialogOpen.value = true
}

export async function closeConfirmDialog() {
  confirmDialogOpen.value = false
  await new Promise(resolve => setTimeout(resolve, 100))
  confirmDialogPayload.value = null
}
