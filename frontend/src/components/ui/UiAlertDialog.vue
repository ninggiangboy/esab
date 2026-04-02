<script setup lang="ts">
import {
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogOverlay,
  AlertDialogPortal,
  AlertDialogRoot,
  AlertDialogTitle,
  AlertDialogTrigger,
} from 'radix-vue'

import UiButton from './UiButton.vue'

defineProps<{
  title: string
  description?: string
}>()

const open = defineModel<boolean>({ default: false })
</script>

<template>
  <AlertDialogRoot v-model:open="open">
    <AlertDialogTrigger as-child>
      <slot name="trigger" />
    </AlertDialogTrigger>
    <AlertDialogPortal>
      <AlertDialogOverlay
        class="data-[state=closed]:animate-out data-[state=open]:animate-in data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 fixed inset-0 z-50 bg-black/70"
      />
      <AlertDialogContent
        class="data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 fixed left-[50%] top-[50%] z-50 grid w-full max-w-lg translate-x-[-50%] translate-y-[-50%] gap-4 border bg-background p-6 shadow-lg duration-200 sm:rounded-lg outline-none"
      >
        <AlertDialogTitle class="text-lg font-semibold">
          {{ title }}
        </AlertDialogTitle>
        <AlertDialogDescription v-if="description" class="text-sm text-muted-foreground">
          {{ description }}
        </AlertDialogDescription>
        <slot />
        <div class="flex flex-col-reverse gap-2 sm:flex-row sm:justify-end">
          <AlertDialogCancel as-child>
            <UiButton variant="outline">
              <slot name="cancel">Cancel</slot>
            </UiButton>
          </AlertDialogCancel>
          <AlertDialogAction as-child>
            <UiButton>
              <slot name="action">Continue</slot>
            </UiButton>
          </AlertDialogAction>
        </div>
      </AlertDialogContent>
    </AlertDialogPortal>
  </AlertDialogRoot>
</template>
