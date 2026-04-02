<script setup lang="ts">
import { CircleX, Info } from 'lucide-vue-next'
import {
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogOverlay,
  AlertDialogPortal,
  AlertDialogRoot,
  AlertDialogTitle,
} from 'radix-vue'

import {
  closeConfirmDialog,
  confirmDialogOpen,
  confirmDialogPayload,
} from '@/lib/confirm-dialog'
import { cn } from '@/lib/utils'

import UiButton from './UiButton.vue'

function onOpenChange(next: boolean) {
  if (!next) {
    closeConfirmDialog()
  }
}

function onConfirm() {
  const snap = confirmDialogPayload.value
  void snap?.action?.onClick?.()
  closeConfirmDialog()
}

function onCancel() {
  const data = confirmDialogPayload.value
  data?.cancel?.onClick?.()
  closeConfirmDialog()
}

const payload = confirmDialogPayload
const dialogOpen = confirmDialogOpen
</script>

<template>
  <AlertDialogRoot :open="dialogOpen" @update:open="onOpenChange">
    <AlertDialogPortal>
      <AlertDialogOverlay
        :class="
          cn(
            'fixed inset-0 z-50 bg-black/70',
            'data-[state=closed]:animate-out data-[state=closed]:fade-out-0',
            'data-[state=open]:animate-in data-[state=open]:fade-in-0',
          )
        "
      />
      <AlertDialogContent
        :class="
          cn(
            'fixed left-[50vw] top-1/2 z-50 grid w-full max-w-[calc(100vw-40px)] -translate-x-1/2 -translate-y-1/2 gap-4 rounded-xl border bg-background p-6 shadow-2xl outline-none md:max-w-[425px] dark:border',
            'data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=open]:fade-in-0 data-[state=closed]:fade-out-0',
            'md:data-[state=open]:zoom-in-97 md:data-[state=closed]:zoom-out-97',
          )
        "
      >
        <div v-if="payload" class="flex flex-col gap-4">
          <div class="flex flex-col gap-2">
            <div class="mb-2">
              <Info
                v-if="(payload.variant ?? 'default') === 'default'"
                class="size-6 text-blue-500 dark:text-blue-400"
                aria-hidden="true"
              />
              <CircleX
                v-else
                class="size-6 text-destructive"
                aria-hidden="true"
              />
            </div>
            <AlertDialogTitle class="text-left text-lg font-semibold">
              {{ payload.title || 'Confirm' }}
            </AlertDialogTitle>
            <AlertDialogDescription class="text-left text-sm text-muted-foreground">
              {{ payload.description || 'Are you sure you want to confirm?' }}
            </AlertDialogDescription>
          </div>
          <div class="flex flex-col-reverse gap-2 sm:flex-row sm:justify-end">
            <AlertDialogCancel as-child>
              <UiButton variant="outline" type="button" @click="onCancel">
                {{ payload.cancel?.label ?? 'Cancel' }}
              </UiButton>
            </AlertDialogCancel>
            <AlertDialogAction as-child>
              <UiButton
                :variant="payload.variant === 'destructive' ? 'destructive' : 'default'"
                type="button"
                @click="onConfirm"
              >
                {{ payload.action?.label ?? 'Confirm' }}
              </UiButton>
            </AlertDialogAction>
          </div>
        </div>
      </AlertDialogContent>
    </AlertDialogPortal>
  </AlertDialogRoot>
</template>
